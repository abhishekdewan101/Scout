import com.abhishek101.gamescout.AppVersions
import com.abhishek101.gamescout.Libs
import com.abhishek101.gamescout.Libs.Koin
import com.abhishek101.gamescout.Libs.SqlDelight
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.properties.Properties
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("com.codingfeline.buildkonfig")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config = files("${rootProject.projectDir}/config/detekt/detekt.yml")
    // point to your custom config defining rules to run, overwriting default behavior
    baseline = file("${rootProject.projectDir}/config/detekt/core_baseline.xml")
    // a way of suppressing issues before introducing detekt

    input.setFrom("src/commonMain/kotlin", "src/androidMain/kotlin", "src/iosMain/kotlin")
    reports {
        html.enabled = true // observe findings in your browser with structure and code snippets
        xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
        txt.enabled = true
        // similar to the console output, contains issue signature to manually edit baseline files
    }
}

kotlin {
    android()

    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    ios {
        binaries {
            framework {
                baseName = "core"
                export(Libs.Kermit.common)
                transitiveExport = true
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(SqlDelight.coroutinesExtensions)
                implementation(SqlDelight.runtime)
                implementation(Koin.koinCore)
                implementation(Libs.Coroutines.core)
                implementation(Libs.kotlinxDateTime)
                // Ktor
                implementation(Libs.Ktor.ktorCio)
                implementation(Libs.Ktor.ktorLogging)
                implementation(Libs.Ktor.ktorJson)
                implementation(Libs.Ktor.commonSerialization)
                // Kermit
                api(Libs.Kermit.common)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(SqlDelight.androidDriver)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(SqlDelight.nativeDriver)
                implementation(Libs.Ktor.iosCore)
            }
        }
    }
}

sqldelight {
    database("AppDb") {
        packageName = "com.abhishek101.core.db"
    }
}

buildkonfig {
    packageName = "com.abhishek101.core"
    val properties = Properties()
    try {
        println("Found local.properties so reading from that")
        properties.load(project.rootProject.file("local.properties").inputStream())
    } catch (e: Exception) {
        println("No local.properties found so authentication won't work")
        properties.setProperty("clientId", "")
        properties.setProperty("useTwitchAuthentication", "false")
        properties.setProperty("clientAuthenticationUrl", "")
    }

    defaultConfigs {
        buildConfigField(STRING, "ClientId", properties.getProperty("clientId"))
        buildConfigField(
            BOOLEAN,
            "UseTwitchAuthentication",
            properties.getProperty("useTwitchAuthentication")
        )
        buildConfigField(BOOLEAN, "EnableHttpLogging", "true")
        buildConfigField(
            STRING,
            "ClientAuthenticationUrl",
            properties.getProperty("clientAuthenticationUrl")
        )
    }
}

android {
    compileSdkVersion(AppVersions.targetSdkVersion)
    defaultConfig {
        minSdkVersion(AppVersions.minSdkVersion)
        targetSdkVersion(AppVersions.targetSdkVersion)
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

ktlint {
    version.set("0.41.0")
    outputColorName.set("RED")
    filter {
        exclude { tree ->
            val path = tree.file.path
            listOf(".gradle/", "cache/", "build/generated/", "buildkonfig/").any {
                path.contains(it)
            }
        }
    }
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)

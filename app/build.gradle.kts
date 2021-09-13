import com.abhishek101.gamescout.AppVersions
import com.abhishek101.gamescout.Libs
import com.abhishek101.gamescout.generatedVersionName
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdkVersion(AppVersions.compileSdkVersion)

    signingConfigs {
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
        getByName("debug") {
            keyAlias = properties.getProperty("debugKeyAlias")
            keyPassword = properties.getProperty("debugKeyPassword")
            storeFile =
                file(rootDir.canonicalPath + "/" + properties.getProperty("debugStoreFileName"))
            storePassword = properties.getProperty("debugStorePassword")
        }
        create("release") {
            keyAlias = properties.getProperty("releaseKeyAlias")
            keyPassword = properties.getProperty("releaseKeyPassword")
            storeFile =
                file(rootDir.canonicalPath + "/" + properties.getProperty("releaseStoreFileName"))
            storePassword = properties.getProperty("releaseStorePassword")
        }
    }

    defaultConfig {
        buildTypes {
            debug {
                signingConfig = signingConfigs.getByName("debug")
                versionNameSuffix = "-SNAPSHOT"
            }
            release {
                isMinifyEnabled = false
                signingConfig = signingConfigs.getByName("release")
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        versionName = generatedVersionName()
        applicationId = "com.abhishek101.gamescout"
        minSdk = (AppVersions.minSdkVersion)
        targetSdk = (AppVersions.targetSdkVersion)
        versionCode = AppVersions.generatedVersionCode
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.AndroidX.Compose.version
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        allWarningsAsErrors = false
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(
            "-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
        )
    }
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config = files("${rootProject.projectDir}/config/detekt/detekt.yml")
    // point to your custom config defining rules to run, overwriting default behavior
    baseline = file("${rootProject.projectDir}/config/detekt/app_baseline.xml")
    // a way of suppressing issues before introducing detekt

    reports {
        html.enabled = true // observe findings in your browser with structure and code snippets
        xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
        txt.enabled = true
        // similar to the console output, contains issue signature to manually edit baseline files
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xskip-prerelease-check",
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
        useIR = true
    }
}

ktlint {
    version.set("0.41.0")
    outputColorName.set("RED")
    filter {
        exclude("build/*")
    }
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}

dependencies {
    implementation(project(":core"))

    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.AndroidX.material)

    // compose
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.Accompanist.flowLayout)
    implementation(Libs.AndroidX.Compose.tooling)
    implementation(Libs.AndroidX.Compose.foundation)
    implementation(Libs.AndroidX.Compose.materialIconsExtended)
    androidTestImplementation(Libs.AndroidX.Compose.testing)

    // Navigation
    implementation(Libs.AndroidX.Navigation.composeNavigation)

    // Coroutines
    implementation(Libs.Coroutines.android)
    implementation(Libs.Coroutines.core)

    // Timber
    implementation(Libs.timber)

    // Koin
    implementation(Libs.Koin.koinCore)
    implementation(Libs.Koin.koinAndroid)
    implementation(Libs.Koin.koinCompose)

    // Coil
    implementation(Libs.coil)
    implementation(Libs.Accompanist.drawable)

    // SystemUiController
    implementation(Libs.Accompanist.systemUiController)

    // Pager
    implementation(Libs.Accompanist.composePager)
    implementation(Libs.Accompanist.insets)

    // testing
    testImplementation(Libs.mockk)
    testImplementation(Libs.googleTruth)
    testImplementation(Libs.JUnit.junit)
    testImplementation(Libs.Coroutines.test)
    androidTestImplementation(Libs.AndroidX.Test.Ext.junit)
    androidTestImplementation(Libs.AndroidX.Test.espressoCore)
    androidTestImplementation(Libs.androidMockk)
    androidTestImplementation(Libs.googleTruth)
}

import com.abhishek101.gametracker.AppVersions
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
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

kotlin {
   android()
    ios {
        binaries {
            framework {
                baseName = "core"
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val androidMain by getting
        val iosMain by getting
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
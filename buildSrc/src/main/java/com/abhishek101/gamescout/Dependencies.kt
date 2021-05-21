package com.abhishek101.gamescout

object Versions {
    const val ktlint = "0.40.0"
}

object AppVersions {
    const val compileSdkVersion = 30
    const val minSdkVersion = 22
    const val targetSdkVersion = 30
    const val versionMajor = 0
    const val versionMinor = 1
    const val versionPatch = 0
    var versionClassifier: String? = null
    const val isSnapshot = true
    const val generatedVersionCode =
        minSdkVersion * 10000000 + versionMajor * 10000 + versionMinor * 100 + versionPatch
}

fun generatedVersionName(): String {
    var versionName =
        "${AppVersions.versionMajor}.${AppVersions.versionMinor}.${AppVersions.versionPatch}"
    if (AppVersions.versionClassifier == null && AppVersions.isSnapshot) {
        AppVersions.versionClassifier = "SNAPSHOT"
    }

    if (AppVersions.versionClassifier != null) {
        versionName += "-${AppVersions.versionClassifier}"
    }
    println("Generated Version Name - $versionName")
    return versionName;
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.0-beta01"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val coil = "com.google.accompanist:accompanist-coil:0.6.2"

    const val mockk = "io.mockk:mockk:1.10.0"
    const val androidMockk = "io.mockk:mockk-android:1.10.0"
    const val kotlinxDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.1.1"

    const val googleTruth = "com.google.truth:truth:1.0.1"

    const val buildKonfig = "com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.7.0"

    object SqlDelight {
        private const val version = "1.5.0"

        const val gradlePlugin = "com.squareup.sqldelight:gradle-plugin:$version"
        const val androidDriver = "com.squareup.sqldelight:android-driver:$version"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:$version"
        const val driver = "com.squareup.sqldelight:sqlite-driver:$version"
        const val runtime = "com.squareup.sqldelight:runtime:$version"
        const val coroutinesExtensions = "com.squareup.sqldelight:coroutines-extensions:$version"
    }

    object Ktor {
        private const val version = "1.5.3"

        const val ktorCio = "io.ktor:ktor-client-cio:$version"
        const val ktorJson = "io.ktor:ktor-client-json:$version"
        const val ktorSerialization = "io.ktor:ktor-client-serialization-jvm:$version"
        const val ktorLogging = "io.ktor:ktor-client-logging:$version"
        const val androidCore = "io.ktor:ktor-client-okhttp:$version"
        const val iosCore = "io.ktor:ktor-client-ios:$version"
        const val commonSerialization = "io.ktor:ktor-client-serialization:$version"
    }

    object Koin {
        private const val version = "3.0.2"

        const val koinCore = "io.insert-koin:koin-core:$version"
        const val koinAndroid = "io.insert-koin:koin-android:$version"
        const val koinCompose = "io.insert-koin:koin-androidx-compose:3.0.1"
    }

    object Accompanist {
        private const val version = "0.9.0"
        const val coil = "dev.chrisbanes.accompanist:accompanist-coil:$version"
        const val insets = "dev.chrisbanes.accompanist:accompanist-insets:$version"
    }

    object Kotlin {
        const val version = "1.4.31"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.4.3-native-mt"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Kermit {
        private const val version = "0.1.9"
        const val common = "co.touchlab:kermit:$version"
    }

    object JUnit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }

    object AndroidX {
        const val material = "com.google.android.material:material:1.3.0"
        object Navigation {
            private const val version = "2.3.3"
            const val composeNavigation = "androidx.navigation:navigation-compose:1.0.0-alpha10"
        }

        object Compose {
            private const val snapshot = ""
            const val version = "1.0.0-beta06"

            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val foundation = "androidx.compose.foundation:foundation:${version}"
            const val layout = "androidx.compose.foundation:foundation-layout:${version}"

            const val ui = "androidx.compose.ui:ui:${version}"
            const val material = "androidx.compose.material:material:${version}"
            const val materialIconsExtended =
                "androidx.compose.material:material-icons-extended:${version}"
            const val tooling = "androidx.compose.ui:ui-tooling:${version}"
            const val testing = "androidx.compose.ui:ui-test-junit4:$version"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"

            object Ext {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
        }
    }
}

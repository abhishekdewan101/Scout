package com.abhishek101.gamescout

object Versions {
    const val ktlint = "0.40.0"
}

object AppVersions {
    const val compileSdkVersion = 30
    const val minSdkVersion = 22
    const val targetSdkVersion = 30
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.0-alpha12"
    const val jdkDesugar = "com.android.tools:desugar_jdk_libs:1.0.9"

    const val material = "com.google.android.material:material:1.1.0"

    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val coil = "com.google.accompanist:accompanist-coil:0.6.2"

    const val mockk = "io.mockk:mockk:1.10.0"
    const val androidMockk = "io.mockk:mockk-android:1.10.0"
    const val kotlinxDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:0.1.1"

    const val googleTruth = "com.google.truth:truth:1.0.1"

    const val buildKonfig = "com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.7.0"

    object SqlDelight {
        private const val version = "1.4.4"

        const val gradlePlugin = "com.squareup.sqldelight:gradle-plugin:$version"
        const val androidDriver = "com.squareup.sqldelight:android-driver:$version"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:$version"
        const val driver = "com.squareup.sqldelight:sqlite-driver:$version"
        const val runtime = "com.squareup.sqldelight:runtime:$version"
        const val coroutinesExtensions = "com.squareup.sqldelight:coroutines-extensions:$version"
    }

    object Ktor {
        private const val version = "1.5.2"

        const val ktorCio = "io.ktor:ktor-client-cio:$version"
        const val ktorJson = "io.ktor:ktor-client-json:$version"
        const val ktorSerialization = "io.ktor:ktor-client-serialization-jvm:$version"
        const val ktorLogging = "io.ktor:ktor-client-logging:$version"
        const val androidCore = "io.ktor:ktor-client-okhttp:$version"
        const val iosCore = "io.ktor:ktor-client-ios:$version"
        const val commonSerialization = "io.ktor:ktor-client-serialization:$version"
    }

    object Koin {
        private const val version = "3.0.1-beta-1"

        const val koinCore = "io.insert-koin:koin-core:$version"
        const val koinAndroid = "io.insert-koin:koin-android:$version"
        const val koinCompose = "io.insert-koin:koin-androidx-compose:$version"
    }

    object Accompanist {
        private const val version = "0.6.0"
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
        private const val version = "1.4.2-native-mt"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object OkHttp {
        private const val version = "4.9.0"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val logging = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Kermit {
        private const val version = "0.1.8"
        const val common = "co.touchlab:kermit:$version"
    }

    object JUnit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.3.0-beta01"
        const val palette = "androidx.palette:palette:1.0.0"

        const val coreKtx = "androidx.core:core-ktx:1.3.2"

        const val material = "com.google.android.material:material:1.3.0"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.0-alpha03"
        }

        object Constraint {
            const val constraintLayoutCompose =
                "androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha03"
        }

        object Navigation {
            private const val version = "2.3.3"

            const val fragmentNavigation = "androidx.navigation:navigation-fragment:$version"
            const val uiNavigation = "androidx.navigation:navigation-ui:$version"
            const val composeNavigation = "androidx.navigation:navigation-compose:1.0.0-alpha08"
        }

        object Compose {
            private const val snapshot = ""
            const val version = "1.0.0-beta02"

            @get:JvmStatic
            val snapshotUrl: String
                get() = "https://androidx.dev/snapshots/builds/$snapshot/artifacts/repository/"

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

        object Lifecycle {
            private const val version = "2.3.0"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val lifecyleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0"
            const val viewModelCompose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha02"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
        }

        object Room {
            private const val version = "2.2.6"
            const val runtime = "androidx.room:room-runtime:${version}"
            const val ktx = "androidx.room:room-ktx:${version}"
            const val compiler = "androidx.room:room-compiler:${version}"
        }
    }
}

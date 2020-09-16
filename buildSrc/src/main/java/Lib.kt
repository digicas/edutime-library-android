object Lib {

    //region Access definitions
    val kotlin = Kotlin
    val android = Android
    val other = Other
    //endregion

    object V {
        const val kotlin = "1.3.72"
        const val coroutines = "1.3.8"
        const val gradle = "4.1.0-rc03"

        object AndroidX {
            const val startup = "1.0.0-alpha01"
            const val activity = "1.2.0-alpha06"
            const val core = "1.5.0-alpha01"
        }

        const val teanity = "1.2.0-alpha01"
    }

    object Kotlin {
        const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${V.kotlin}"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${V.kotlin}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${V.coroutines}"
    }

    object Android {
        const val build = "com.android.tools.build:gradle:${V.gradle}"
        const val startup = "androidx.startup:startup-runtime:${V.AndroidX.startup}"
        const val activity = "androidx.activity:activity:${V.AndroidX.activity}"
        const val core = "androidx.core:core-ktx:${V.AndroidX.core}"
    }

    object Other {
        const val teanity = "com.skoumal:teanity-plugin:1.0.8"
        const val teanityModules = V.teanity
    }

}
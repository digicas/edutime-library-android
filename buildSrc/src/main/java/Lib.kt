object Lib {

    //region Access definitions
    val kotlin = Kotlin
    val android = Android
    val other = Other
    //endregion

    object V {
        const val kotlin = "1.3.72"
        const val coroutines = "1.3.7"
        const val gradle = "4.1.0-beta02"

        object AndroidX {
            const val recycler = "1.1.0"
            const val work = "2.4.0-beta01"
            const val room = "2.2.5"
            const val navigation = "2.3.0-beta01"
            const val lifecycle = "2.3.0-alpha03"
            const val startup = "1.0.0-alpha01"
        }

        const val twine = "0.1.+"
        const val teanity = "1.2.0-alpha01"
        const val kotpref = "2.9.1"
    }

    object Kotlin {
        const val gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${V.kotlin}"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${V.kotlin}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${V.coroutines}"
    }

    object Android {
        const val build = "com.android.tools.build:gradle:${V.gradle}"
        const val recycler = "androidx.recyclerview:recyclerview:${V.AndroidX.recycler}"
        const val work = "androidx.work:work-runtime-ktx:${V.AndroidX.work}"
        const val navigationPlugin =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${V.AndroidX.navigation}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${V.AndroidX.lifecycle}"
        const val startup = "androidx.startup:startup-runtime:${V.AndroidX.startup}"
    }

    object Other {
        const val teanity = "com.skoumal:teanity-plugin:1.0.8"
        const val teanityModules = V.teanity
        const val twine = "com.skoumal:twine-strings-generator-plugin:${V.twine}"
        const val kotpref = "com.chibatching.kotpref:kotpref:${V.kotpref}"
        const val teagger = "com.skoumal:teagger:db7f71d6b3"
    }

}
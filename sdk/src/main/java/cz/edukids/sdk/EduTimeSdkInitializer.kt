package cz.edukids.sdk

import android.content.Context
import androidx.startup.Initializer

class EduTimeSdkInitializer : Initializer<EduTimeSdk> {

    override fun create(context: Context) = EduTimeSdk(context)
    override fun dependencies(): List<Class<out Initializer<*>>> = listOf()

}
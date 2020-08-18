package cz.edukids.sdk

import android.content.Context
import androidx.startup.Initializer

class EduSdkInitializer : Initializer<EduSdk> {

    override fun create(context: Context) = EduSdk(context)
    override fun dependencies(): List<Class<out Initializer<*>>> = listOf()

}
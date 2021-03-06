package cz.edukids.sdk.app

import android.app.Application
import android.util.Log
import cz.edukids.sdk.model.logging.SdkLogger
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        SdkLogger.level(Log.VERBOSE)
    }

}
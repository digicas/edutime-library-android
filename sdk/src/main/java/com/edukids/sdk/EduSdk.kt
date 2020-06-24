package com.edukids.sdk

import android.content.Context
import android.content.Intent
import java.lang.ref.WeakReference

class EduSdk private constructor(
    private val _context: WeakReference<Context>
) {

    private val context get() = _context.get()

    fun getInstance(intent: Intent): EduSdkInstance {
        TODO()
    }

    companion object {

        private var sdk: WeakReference<EduSdk?>? = null // this WR is prolly unnecessary
            set(value) {
                field?.clear()
                field = value
            }

        operator fun invoke(context: Context) = sdk?.get() ?: let {
            EduSdk(WeakReference(context.applicationContext)).also {
                sdk = WeakReference(it)
            }
        }

    }

}
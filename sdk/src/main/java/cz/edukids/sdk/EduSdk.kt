package cz.edukids.sdk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cz.edukids.sdk.comms.SuspendingWaitRegistry
import cz.edukids.sdk.dispatcher.EduSdkResolver
import cz.edukids.sdk.internal.EduSdkInstanceImpl
import java.lang.ref.WeakReference

class EduSdk private constructor(
    private val _context: WeakReference<Context>
) {

    internal val context get() = _context.get()

    internal val waitRegistry = SuspendingWaitRegistry()

    @Throws(IllegalStateException::class)
    fun getNewInstance(intent: Intent): EduSdkInstance {
        val key = EduSdkResolver.find()
            .inside(intent.extras ?: Bundle.EMPTY)
            .resolveInstance()

        check(key != null) {
            "Cannot return a new instance without any key present. You weren't launched through the launcher."
        }

        return EduSdkInstanceImpl(key, this)
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

        @Throws(KotlinNullPointerException::class)
        @JvmName("getInstance")
        @JvmStatic
        operator fun invoke() = sdk?.get()!!

    }

}
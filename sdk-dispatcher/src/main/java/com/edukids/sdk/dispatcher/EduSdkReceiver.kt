package com.edukids.sdk.dispatcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import com.edukids.sdk.model.internal.InstanceKey
import com.edukids.sdk.model.logging.SdkLogger
import com.edukids.sdk.model.logging.e

abstract class EduSdkReceiver : BroadcastReceiver() {

    abstract val action: String

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            action -> onReceive(context ?: return, intent.extras ?: return)
            else -> SdkLogger.e("Received [intent=${intent}] with invalid [action=${intent?.action}], expected [action=$action]")
        }
    }

    private fun onReceive(context: Context, extras: Bundle) {
        val resolver = EduSdkResolver.find().inside(extras)
        onReceive(context, resolver.resolve(), resolver.resolveInstance())
    }

    open fun onReceive(context: Context, extras: List<Parcelable>, instance: InstanceKey?) {
        extras.forEach { onReceive(context, it, instance) }
    }

    open fun onReceive(context: Context, extra: Parcelable, instance: InstanceKey?) = Unit

}
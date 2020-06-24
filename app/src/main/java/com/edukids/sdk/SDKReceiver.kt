package com.edukids.sdk

import android.content.Context
import android.content.Intent
import timber.log.Timber

class SDKReceiver : EduSdkReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        val extras = intent.extras ?: return
        val sample = extras.getLong("sample")
        Timber.d("<- Received (SDK) [parcelable=${sample}]")

        val intent = Intent("com.edukids.sdk.ACTION_CONTACT_DATA_SOURCE")
            .setClassName("com.edukids.launcher", "com.edukids.comms.SDKBroadcastReceiver")
            .putExtra("sample", sample)
        Timber.d("-> Sending (SDK) [parcelable=${sample}]")
        context.sendBroadcast(intent)
    }
}
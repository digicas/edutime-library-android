package com.edukids.sdk.comms

import android.content.Context
import android.os.Parcelable
import com.edukids.sdk.dispatcher.EduSdkReceiver
import com.edukids.sdk.model.internal.Constants

class SdkReceiver : EduSdkReceiver() {

    override val action = Constants.ACTION_CONTACT_DATA_SOURCE

    override fun onReceive(context: Context, extras: List<Parcelable>) {
    }

}
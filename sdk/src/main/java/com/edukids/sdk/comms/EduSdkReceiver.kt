package com.edukids.sdk.comms

import android.content.Context
import android.os.Parcelable
import com.edukids.sdk.dispatcher.EduSdkReceiver
import com.edukids.sdk.dispatcher.EduSdkResolver
import com.edukids.sdk.model.internal.Constants
import com.edukids.sdk.model.internal.InstanceKey
import com.edukids.sdk.model.logging.SdkLogger
import com.edukids.sdk.model.logging.d

class SdkReceiver : EduSdkReceiver() {

    override val action = Constants.ACTION_RECEIVE_DATA

    override suspend fun onReceive(context: Context, extra: Parcelable, instance: InstanceKey?) {
        SdkLogger.d("<- SDK received a parcel $extra")
        // STOPSHIP: 25/06/2020 this sample code needs to be removed
        EduSdkResolver.dispatch()
            .setAction(Constants.ACTION_CONTACT_DATA_SOURCE)
            .setPackageName(Constants.PACKAGE_LAUNCHER)
            .setTargetClass(Constants.CLASS_LAUNCHER_RECEIVER)
            .createDispatcher()
            .put(extra)
            .dispatch(context)
    }

}
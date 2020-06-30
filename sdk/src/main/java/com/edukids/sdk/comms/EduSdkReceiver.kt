package com.edukids.sdk.comms

import android.content.Context
import android.os.Parcelable
import com.edukids.sdk.EduSdk
import com.edukids.sdk.dispatcher.EduSdkReceiver
import com.edukids.sdk.model.internal.EduConstants
import com.edukids.sdk.model.internal.InstanceKey

class SdkReceiver : EduSdkReceiver() {

    override val action = EduConstants.Action.RECEIVE_DATA

    override suspend fun onReceive(context: Context, extra: Parcelable, instance: InstanceKey?) {
        EduSdk().waitRegistry.push(extra)
    }

}
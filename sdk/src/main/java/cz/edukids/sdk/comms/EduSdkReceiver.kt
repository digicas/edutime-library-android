package cz.edukids.sdk.comms

import android.content.Context
import android.os.Parcelable
import cz.edukids.sdk.EduSdk
import cz.edukids.sdk.dispatcher.EduSdkReceiver
import cz.edukids.sdk.model.internal.EduConstants
import cz.edukids.sdk.model.internal.InstanceKey

class SdkReceiver : EduSdkReceiver() {

    override val action = EduConstants.Action.RECEIVE_DATA

    override suspend fun onReceive(context: Context, extra: Parcelable, instance: InstanceKey?) {
        EduSdk().waitRegistry.push(extra)
    }

}
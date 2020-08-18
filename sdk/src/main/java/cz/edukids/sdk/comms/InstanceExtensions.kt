package cz.edukids.sdk.comms

import cz.edukids.sdk.dispatcher.EduSdkResolver
import cz.edukids.sdk.model.internal.EduConstants
import cz.edukids.sdk.model.internal.InstanceKey

fun InstanceKey.dispatch() = EduSdkResolver.dispatch(this)
    .setAction(EduConstants.Action.CONTACT_DATA_SOURCE)
    .setTargetClass(EduConstants.Origin.RECEIVER)
    .setPackageName(EduConstants.Origin.LAUNCHER)
    .createDispatcher()

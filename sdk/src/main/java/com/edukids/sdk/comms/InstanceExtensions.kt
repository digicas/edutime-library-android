package com.edukids.sdk.comms

import com.edukids.sdk.dispatcher.EduSdkResolver
import com.edukids.sdk.model.internal.EduConstants
import com.edukids.sdk.model.internal.InstanceKey

fun InstanceKey.dispatch() = EduSdkResolver.dispatch(this)
    .setAction(EduConstants.Action.CONTACT_DATA_SOURCE)
    .setTargetClass(EduConstants.Origin.RECEIVER)
    .setPackageName(EduConstants.Origin.LAUNCHER)
    .createDispatcher()

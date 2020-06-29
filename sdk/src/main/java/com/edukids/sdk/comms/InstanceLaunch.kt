package com.edukids.sdk.comms

import com.edukids.sdk.dispatcher.EduSdkResolver
import com.edukids.sdk.model.internal.Constants
import com.edukids.sdk.model.internal.InstanceKey

fun InstanceKey.dispatch() = EduSdkResolver.dispatch(this)
    .setAction(Constants.ACTION_CONTACT_DATA_SOURCE)
    .setTargetClass(Constants.CLASS_LAUNCHER_RECEIVER)
    .setPackageName(Constants.PACKAGE_LAUNCHER)
    .createDispatcher()

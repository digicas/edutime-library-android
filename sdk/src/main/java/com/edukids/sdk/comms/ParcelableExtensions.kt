package com.edukids.sdk.comms

import android.os.Parcelable
import com.edukids.sdk.model.EduMissionContract
import com.edukids.sdk.model.EduMissionStartParams
import com.edukids.sdk.model.EduTaskOrder

fun Parcelable.getResponseType() = when (this) {
    is EduTaskOrder -> this.target
    is EduMissionStartParams -> EduMissionContract::class.java
    else -> this::class.java
}
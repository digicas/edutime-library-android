package cz.edukids.sdk.comms

import android.os.Parcelable
import cz.edukids.sdk.model.EduMissionContract
import cz.edukids.sdk.model.EduMissionStartParams
import cz.edukids.sdk.model.EduTaskOrder

fun Parcelable.getResponseType() = when (this) {
    is EduTaskOrder -> this.target
    is EduMissionStartParams -> EduMissionContract::class.java
    else -> this::class.java
}
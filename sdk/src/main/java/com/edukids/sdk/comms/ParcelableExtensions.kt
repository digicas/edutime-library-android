package com.edukids.sdk.comms

import android.os.Parcelable
import com.edukids.sdk.model.EduTaskOrder

fun Parcelable.getResponseType() = when (this) {
    is EduTaskOrder -> this.target
    else -> this::class.java
}
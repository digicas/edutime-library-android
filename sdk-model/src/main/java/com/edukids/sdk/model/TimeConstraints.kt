package com.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeConstraints(
    /**
     * Datetime at which the app will be (or was) started.
     */
    val startTimestamp: Long,

    /**
     * Datetime at which the app will be stopped if was previously running.
     */
    val stopTimestamp: Long
) : Parcelable
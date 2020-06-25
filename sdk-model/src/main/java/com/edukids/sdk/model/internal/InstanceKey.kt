package com.edukids.sdk.model.internal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
inline class InstanceKey(
    val key: String
) : Parcelable
package com.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkillSet(
    val failed: Map<String, Int>,
    val succeeded: Map<String, Int>
) : Parcelable
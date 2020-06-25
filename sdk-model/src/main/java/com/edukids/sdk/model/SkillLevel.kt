package com.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkillLevel(
    val mastered: SkillSet,
    val inProgress: SkillSet,
    val failed: SkillSet
) : Parcelable

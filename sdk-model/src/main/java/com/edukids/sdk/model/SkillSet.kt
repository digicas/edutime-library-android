package com.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkillSet(
    val skill: List<String>,
    val categories: List<String>,
    val subcategories: List<String>,
    val disciplines: List<String>
) : Parcelable
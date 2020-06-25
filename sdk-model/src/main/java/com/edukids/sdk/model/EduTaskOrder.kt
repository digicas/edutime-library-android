package com.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class EduTaskOrder : Parcelable {

    // data
    CURRENCY_STATS,
    SKILL_LEVEL,

    // categories
    SCREEN_TIME_CATEGORY,

    // constraints
    SCREEN_TIME_CONSTRAINTS,
    TIME_CONSTRAINTS,

}
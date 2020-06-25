package com.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScreenTimeCategoryConstraints(
    /**
     * Category currently assigned to this time period
     * */
    val currentCategory: ScreenTimeCategory,

    /**
     * Category currently assigned to this app
     * */
    val assignedCategory: ScreenTimeCategory,

    /**
     * Available categories under which the app can be listed
     * */
    val availableCategories: List<ScreenTimeCategory>
) : Parcelable
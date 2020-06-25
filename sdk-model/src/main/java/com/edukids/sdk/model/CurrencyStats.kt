package com.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrencyStats(
    val currentAmount: Long,
    val earnedInInstance: Long,
    val currentlyEarnable: Long
) : Parcelable
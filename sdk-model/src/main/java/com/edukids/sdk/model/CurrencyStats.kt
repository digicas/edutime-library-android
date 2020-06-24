package com.edukids.sdk.model

import android.os.Parcelable

interface CurrencyStats : Parcelable {

    val currentAmount: Long
    val earnedInInstance: Long
    val currentlyEarnable: Long

}
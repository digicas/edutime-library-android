package cz.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrencyStats(
    val currentAmount: Double,
    val earnedInInstance: Double
) : Parcelable
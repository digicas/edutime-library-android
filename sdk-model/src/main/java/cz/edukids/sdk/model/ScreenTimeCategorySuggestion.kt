package cz.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScreenTimeCategorySuggestion(
    /**
     * Requested category id; it's displayed as a suggestion in the parent's app
     * */
    val id: String
) : Parcelable
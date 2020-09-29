package cz.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScreenTimeCategory(
    /**
     * Category might be locked by the child's parent
     * */
    val isLocked: Boolean,

    /**
     * Category is currently selected as default
     * */
    val isSelected: Boolean,

    /**
     * Ids are normalized and unique
     * */
    val id: String,

    val name: String
) : Parcelable
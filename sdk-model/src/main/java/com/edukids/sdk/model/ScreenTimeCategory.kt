package com.edukids.sdk.model

import android.os.Parcelable

interface ScreenTimeCategory : Parcelable {

    /* Category might be locked by the child's parent */
    val isLocked: Boolean

    /* Category is currently selected as default */
    val isSelected: Boolean

    /* Ids are normalized and unique */
    val id: String
    val name: String
    val description: String

}
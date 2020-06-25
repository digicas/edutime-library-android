package com.edukids.sdk.model

import android.os.Parcelable

interface ScreenTimeCategorySuggestion : Parcelable {

    /**
     * Requested category id; it's displayed as a suggestion in the parent's app
     * */
    val id: String

}
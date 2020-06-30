package com.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EduError(
    val code: Int,
    /**
     * Does not provide information that should be displayed to the user, quite the contrary. Keep
     * these messages to developer builds only, implement [code] based maps with your own messages.
     * */
    override val message: String,
    val target: Class<out Parcelable>
) : RuntimeException(), Parcelable {

    companion object {

        const val ERR_UNKNOWN = -1

    }

}
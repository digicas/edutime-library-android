package cz.edukids.sdk.model.internal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InstanceKey(
    val key: String
) : Parcelable
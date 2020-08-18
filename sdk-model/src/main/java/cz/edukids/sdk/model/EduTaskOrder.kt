package cz.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EduTaskOrder(
     val target: Class<out Parcelable>
) : Parcelable
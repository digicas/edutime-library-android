package cz.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EduMissionContract(
    /**
     * Id of the created contract.
     * */
    val id: String
) : Parcelable
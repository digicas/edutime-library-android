package cz.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EduMissionFinishParams(
    /**
     * Tells the SDK which contract does it want to end. This parameter is received from the result
     * of [EduMission.start]
     *
     * @see [EduMissionContract]
     * */
    val contractId: String,

    /**
     * Notifies the sdk that the mission was successful, so the underlying sdk knows that no
     * further exercise are necessary
     * */
    val isSuccess: Boolean,

    /**
     * Reports number of points acquired by completing the exercise. Also known as Edupoints
     * */
    val pointsAcquired: Int,

    /**
     * Permits app the send the sdk additional data to accompany the "finish" entry
     * Data has to be serialized to string. The structure is **not** looked at by the SDK.
     * */
    val dataBundle: String? = null
) : Parcelable
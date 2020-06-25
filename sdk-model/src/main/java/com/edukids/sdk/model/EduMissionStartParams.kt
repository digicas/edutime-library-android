package com.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EduMissionStartParams(
    /**
     * Notifies the sdk that the task is going to be restarted, possibly subtracting points from
     * the total or adding smaller bonus amount
     * */
    val isRetry: Boolean,

    /**
     * Skill description. Conforms to no particular pattern.
     * (eg. addition single numbers over 10)
     * */
    val skills: List<String>,

    /**
     * States exact task type which is being performed.
     * (eg. math pyramid)
     * */
    val eduTaskType: String,

    /**
     * List of disciplines that this particular exercise requires.
     * (eg. math, english, ...)
     * */
    val disciplines: List<String>? = null,

    /**
     * Permits app to send the sdk additional data to accompany the "start" entry
     * Data has to be serialized to string. The structure is **not** looked at by the SDK.
     * */
    val dataBundle: String? = null
) : Parcelable
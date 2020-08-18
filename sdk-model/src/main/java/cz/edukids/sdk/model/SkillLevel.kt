package cz.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SkillLevel(
    val firstTry: SkillSet,
    val retry: SkillSet
) : Parcelable

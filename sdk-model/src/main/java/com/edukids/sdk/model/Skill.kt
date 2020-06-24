package com.edukids.sdk.model

import android.os.Parcelable

interface SkillLevel : Parcelable {

    val mastered: SkillSet
    val inProgress: SkillSet
    val failed: SkillSet

}

interface SkillSet : Parcelable {

    val skill: List<String>
    val categories: List<String>
    val subcategories: List<String>
    val disciplines: List<String>

}
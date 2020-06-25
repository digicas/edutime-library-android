package com.edukids.sdk.internal

import com.edukids.sdk.EduMission
import com.edukids.sdk.EduSdk
import com.edukids.sdk.EduSdkInstance
import com.edukids.sdk.model.CurrencyStats
import com.edukids.sdk.model.ScreenTimeCategoryConstraints
import com.edukids.sdk.model.ScreenTimeCategorySuggestion
import com.edukids.sdk.model.TimeConstraints
import com.edukids.sdk.model.internal.InstanceKey
import java.util.concurrent.Future

internal class EduSdkInstanceImpl(
    private val key: InstanceKey,
    private val sdk: EduSdk
) : EduSdkInstance {

    override suspend fun getTimeConstraints(): Result<TimeConstraints> {
        TODO()
    }

    override suspend fun getScreenTimeCategoryConstraints(): Result<ScreenTimeCategoryConstraints> {
        TODO()
    }

    override suspend fun getCurrencyStats(): Result<CurrencyStats> {
        TODO()
    }


    override fun getTimeConstraintsAsync(): Future<TimeConstraints> {
        TODO()
    }

    override fun getScreenTimeCategoryConstraintsAsync(): Future<ScreenTimeCategoryConstraints> {
        TODO()
    }

    override fun getCurrencyStatsAsync(): Future<CurrencyStats> {
        TODO()
    }


    override fun suggestCorrectCategory(suggestion: ScreenTimeCategorySuggestion) {
        TODO()
    }

    override fun getMission(): EduMission {
        return EduMissionImpl(key, sdk)
    }

}
package com.edukids.sdk

import com.edukids.sdk.model.CurrencyStats
import com.edukids.sdk.model.EduMission
import com.edukids.sdk.model.ScreenTimeCategoryConstraints
import com.edukids.sdk.model.TimeConstraints
import java.util.concurrent.Future

interface EduSdkInstance {

    suspend fun getTimeConstraints(): TimeConstraints
    suspend fun getScreenTimeCategoryConstraints(): ScreenTimeCategoryConstraints
    suspend fun getCurrencyStats(): CurrencyStats

    fun getTimeConstraintsAsync(): Future<TimeConstraints>
    fun getScreenTimeCategoryConstraintsAsync(): Future<ScreenTimeCategoryConstraints>
    fun getCurrencyStatsAsync(): Future<CurrencyStats>

    fun getMission(): EduMission

}
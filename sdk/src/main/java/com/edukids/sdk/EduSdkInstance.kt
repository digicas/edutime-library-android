package com.edukids.sdk

import com.edukids.sdk.model.CurrencyStats
import com.edukids.sdk.model.ScreenTimeCategoryConstraints
import com.edukids.sdk.model.ScreenTimeCategorySuggestion
import com.edukids.sdk.model.TimeConstraints
import java.util.concurrent.Future

interface EduSdkInstance {

    /**
     * Requests this app's constraints from the launcher.
     * @see [TimeConstraints]
     * */
    suspend fun getTimeConstraints(): Result<TimeConstraints>

    /**
     * Requests this app's screen time category constraints.
     *
     * @return Collection of constraints. App might choose to prepare tasks beforehand if it's not
     * its time yet, or suggest category changes based on
     * [ScreenTimeCategoryConstraints.assignedCategory].
     * */
    suspend fun getScreenTimeCategoryConstraints(): Result<ScreenTimeCategoryConstraints>

    /**
     * Requests current currency stats. App can suggest to the user that no more TimeCoins will be
     * awarded, hence completing the exercises is pointless in that way. It can also adjust the
     * exercises to maximize the time user spends in the app by cherry picking longer tasks and
     * vice versa.
     * */
    suspend fun getCurrencyStats(): Result<CurrencyStats>


    /** @see [getTimeConstraints] */
    fun getTimeConstraintsAsync(): Future<TimeConstraints>

    /** @see [getScreenTimeCategoryConstraints] */
    fun getScreenTimeCategoryConstraintsAsync(): Future<ScreenTimeCategoryConstraints>

    /** @see [getCurrencyStats] */
    fun getCurrencyStatsAsync(): Future<CurrencyStats>


    /**
     * Tries to set current category to the provided one. It might however fail to do so under some
     * circumstances (ie. Category is locked by child's parent, etc)
     * */
    fun suggestCorrectCategory(suggestion: ScreenTimeCategorySuggestion)

    /**
     * @return a mission interface for dispatching singular tasks to the app so user can be awarded
     * with TimeCoins
     * */
    fun getMission(): EduMission

}
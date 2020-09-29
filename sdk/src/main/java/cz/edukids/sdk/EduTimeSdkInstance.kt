package cz.edukids.sdk

import cz.edukids.sdk.model.*
import java.util.concurrent.Future

interface EduTimeSdkInstance {

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
     * [ScreenTimeCategoryInfo.assignedCategory].
     * */
    suspend fun getScreenTimeCategoryConstraints(): Result<ScreenTimeCategoryInfo>

    /**
     * Requests current currency stats. App can suggest to the user that no more TimeCoins will be
     * awarded, hence completing the exercises is pointless in that way. It can also adjust the
     * exercises to maximize the time user spends in the app by cherry picking longer tasks and
     * vice versa.
     * */
    suspend fun getCurrencyStats(): Result<CurrencyStats>

    /**
     * Request skill level of current user. App should be able to deduce the skill level of the
     * current user based on the stats provided by the SDK.
     * It should be however called only at launch time as it can take significant time to respond
     * and will provide full statistics every time.
     * This provision of full stats should be beneficial for apps that haven't been launched in a
     * long time or user completed dozens of exercises since the last launch.
     * */
    suspend fun getSkillLevel(): Result<SkillLevel>


    /** @see [getTimeConstraints] */
    fun getTimeConstraintsAsync(): Future<TimeConstraints>

    /** @see [getScreenTimeCategoryConstraints] */
    fun getScreenTimeCategoryConstraintsAsync(): Future<ScreenTimeCategoryInfo>

    /** @see [getCurrencyStats] */
    fun getCurrencyStatsAsync(): Future<CurrencyStats>

    /** @see [getSkillLevel] */
    fun getSkillLevelAsync(): Future<SkillLevel>


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
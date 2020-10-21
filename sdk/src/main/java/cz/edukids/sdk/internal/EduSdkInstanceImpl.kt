package cz.edukids.sdk.internal

import cz.edukids.sdk.EduMission
import cz.edukids.sdk.EduTimeSdk
import cz.edukids.sdk.EduTimeSdkInstance
import cz.edukids.sdk.async.asFuture
import cz.edukids.sdk.comms.dispatch
import cz.edukids.sdk.model.*
import cz.edukids.sdk.model.internal.InstanceKey
import kotlinx.coroutines.*
import java.util.concurrent.Future

internal class EduSdkInstanceImpl(
    private val key: InstanceKey,
    private val sdk: EduTimeSdk
) : EduTimeSdkInstance {

    private val scope = MainScope()
    private val dispatcher = Dispatchers.Default

    override suspend fun getTimeConstraints(): Result<TimeConstraints> {
        withContext(dispatcher) {
            key.dispatch()
                .put(EduTaskOrder(TimeConstraints::class.java))
                .dispatch(sdk.context!!)
        }
        return sdk.waitRegistry.runCatching { await() }
    }

    override suspend fun getScreenTimeCategoryInfo(): Result<ScreenTimeCategoryInfo> {
        withContext(dispatcher) {
            key.dispatch()
                .put(EduTaskOrder(ScreenTimeCategoryInfo::class.java))
                .dispatch(sdk.context!!)
        }
        return sdk.waitRegistry.runCatching { await() }
    }

    override suspend fun getCurrencyStats(): Result<CurrencyStats> {
        withContext(dispatcher) {
            key.dispatch()
                .put(EduTaskOrder(CurrencyStats::class.java))
                .dispatch(sdk.context!!)
        }
        return sdk.waitRegistry.runCatching { await() }
    }

    override suspend fun getSkillLevel(): Result<SkillLevel> {
        withContext(dispatcher) {
            key.dispatch()
                .put(EduTaskOrder(SkillLevel::class.java))
                .dispatch(sdk.context!!)
        }
        return sdk.waitRegistry.runCatching { await() }
    }

    override fun getTimeConstraintsAsync(): Future<TimeConstraints> {
        return scope
            .async(dispatcher) { getTimeConstraints().getOrThrow() }
            .asFuture()
    }

    override fun getScreenTimeCategoryInfoAsync(): Future<ScreenTimeCategoryInfo> {
        return scope
            .async(dispatcher) { getScreenTimeCategoryInfo().getOrThrow() }
            .asFuture()
    }

    override fun getCurrencyStatsAsync(): Future<CurrencyStats> {
        return scope
            .async(dispatcher) { getCurrencyStats().getOrThrow() }
            .asFuture()
    }

    override fun getSkillLevelAsync(): Future<SkillLevel> {
        return scope
            .async(dispatcher) { getSkillLevel().getOrThrow() }
            .asFuture()
    }

    override fun suggestCorrectCategory(suggestion: ScreenTimeCategorySuggestion) {
        scope.launch(dispatcher) {
            key.dispatch()
                .put(suggestion)
                .dispatch(sdk.context!!)
        }
    }

    override fun getMission(): EduMission {
        return EduMissionImpl(key, sdk)
    }

}


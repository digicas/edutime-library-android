package com.edukids.sdk.internal

import com.edukids.sdk.EduMission
import com.edukids.sdk.EduSdk
import com.edukids.sdk.EduSdkInstance
import com.edukids.sdk.async.asFuture
import com.edukids.sdk.comms.dispatch
import com.edukids.sdk.model.*
import com.edukids.sdk.model.internal.InstanceKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.Future

internal class EduSdkInstanceImpl(
    private val key: InstanceKey,
    private val sdk: EduSdk
) : EduSdkInstance {

    private val scope = MainScope()
    private val dispatcher = Dispatchers.Default

    override suspend fun getTimeConstraints(): Result<TimeConstraints> {
        scope.launch(dispatcher) {
            key.dispatch()
                .put(EduTaskOrder.TIME_CONSTRAINTS)
                .dispatch(sdk.context!!)
        }
        return sdk.waitRegistry.runCatching { await<TimeConstraints>() }
    }

    override suspend fun getScreenTimeCategoryConstraints(): Result<ScreenTimeCategoryConstraints> {
        scope.launch(dispatcher) {
            key.dispatch()
                .put(EduTaskOrder.SCREEN_TIME_CONSTRAINTS)
                .dispatch(sdk.context!!)
        }
        return sdk.waitRegistry.runCatching { await<ScreenTimeCategoryConstraints>() }
    }

    override suspend fun getCurrencyStats(): Result<CurrencyStats> {
        scope.launch(dispatcher) {
            key.dispatch()
                .put(EduTaskOrder.CURRENCY_STATS)
                .dispatch(sdk.context!!)
        }
        return sdk.waitRegistry.runCatching { await<CurrencyStats>() }
    }


    override fun getTimeConstraintsAsync(): Future<TimeConstraints> {
        return scope
            .async(dispatcher) { getTimeConstraints().getOrThrow() }
            .asFuture()
    }

    override fun getScreenTimeCategoryConstraintsAsync(): Future<ScreenTimeCategoryConstraints> {
        return scope
            .async(dispatcher) { getScreenTimeCategoryConstraints().getOrThrow() }
            .asFuture()
    }

    override fun getCurrencyStatsAsync(): Future<CurrencyStats> {
        return scope
            .async(dispatcher) { getCurrencyStats().getOrThrow() }
            .asFuture()
    }


    override fun suggestCorrectCategory(suggestion: ScreenTimeCategorySuggestion) {
        key.dispatch()
            .put(suggestion)
            .dispatch(sdk.context!!)
    }

    override fun getMission(): EduMission {
        return EduMissionImpl(key, sdk)
    }

}


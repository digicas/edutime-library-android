package com.edukids.sdk.internal

import com.edukids.sdk.EduMission
import com.edukids.sdk.EduSdk
import com.edukids.sdk.async.asFuture
import com.edukids.sdk.comms.dispatch
import com.edukids.sdk.model.EduMissionComplete
import com.edukids.sdk.model.EduMissionContract
import com.edukids.sdk.model.EduMissionFinishParams
import com.edukids.sdk.model.EduMissionStartParams
import com.edukids.sdk.model.internal.InstanceKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import java.util.concurrent.Future

internal class EduMissionImpl(
    private val key: InstanceKey,
    private val sdk: EduSdk
) : EduMission {

    private val scope = MainScope()
    private val dispatcher = Dispatchers.Default

    override suspend fun start(params: EduMissionStartParams): Result<EduMissionContract> {
        key.dispatch()
            .put(params)
            .dispatch(sdk.context!!)
        return sdk.waitRegistry.runCatching { await<EduMissionContract>() }
    }

    override fun finish(params: EduMissionFinishParams) {
        key.dispatch()
            .put(params)
            .dispatch(sdk.context!!)
    }

    override fun startAsync(params: EduMissionStartParams): Future<EduMissionContract> {
        return scope
            .async(dispatcher) { start(params).getOrThrow() }
            .asFuture()
    }

    override fun complete() {
        key.dispatch()
            .put(EduMissionComplete)
            .dispatch(sdk.context!!)
    }

}
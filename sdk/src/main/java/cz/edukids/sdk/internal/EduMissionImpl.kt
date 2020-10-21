package cz.edukids.sdk.internal

import cz.edukids.sdk.EduMission
import cz.edukids.sdk.EduTimeSdk
import cz.edukids.sdk.async.asFuture
import cz.edukids.sdk.comms.dispatch
import cz.edukids.sdk.model.EduMissionComplete
import cz.edukids.sdk.model.EduMissionContract
import cz.edukids.sdk.model.EduMissionFinishParams
import cz.edukids.sdk.model.EduMissionStartParams
import cz.edukids.sdk.model.internal.InstanceKey
import kotlinx.coroutines.*
import java.util.concurrent.Future

internal class EduMissionImpl(
    private val key: InstanceKey,
    private val sdk: EduTimeSdk
) : EduMission {

    private val scope = MainScope()
    private val dispatcher = Dispatchers.Default

    override suspend fun start(params: EduMissionStartParams): Result<EduMissionContract> {
        withContext(dispatcher) {
            key.dispatch()
                .put(params)
                .dispatch(sdk.context!!)
        }
        return sdk.waitRegistry.runCatching { await() }
    }

    override fun finish(params: EduMissionFinishParams) {
        scope.launch(dispatcher) {
            key.dispatch()
                .put(params)
                .dispatch(sdk.context!!)
        }
    }

    override fun startAsync(params: EduMissionStartParams): Future<EduMissionContract> {
        return scope
            .async(dispatcher) { start(params).getOrThrow() }
            .asFuture()
    }

    override fun complete() {
        scope.launch(dispatcher) {
            key.dispatch()
                .put(EduMissionComplete)
                .dispatch(sdk.context!!)
        }
    }

}
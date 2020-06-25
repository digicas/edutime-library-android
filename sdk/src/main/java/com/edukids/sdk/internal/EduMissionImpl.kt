package com.edukids.sdk.internal

import com.edukids.sdk.EduMission
import com.edukids.sdk.EduSdk
import com.edukids.sdk.model.EduMissionContract
import com.edukids.sdk.model.EduMissionFinishParams
import com.edukids.sdk.model.EduMissionStartParams
import com.edukids.sdk.model.internal.InstanceKey
import java.util.concurrent.Future

internal class EduMissionImpl(
    private val key: InstanceKey,
    private val sdk: EduSdk
) : EduMission {

    override suspend fun start(params: EduMissionStartParams): Result<EduMissionContract> {
        TODO()
    }

    override fun finish(params: EduMissionFinishParams) {
        TODO()
    }

    override fun startAsync(params: EduMissionStartParams): Future<EduMissionContract> {
        TODO()
    }

    override fun complete() {
        TODO()
    }

}
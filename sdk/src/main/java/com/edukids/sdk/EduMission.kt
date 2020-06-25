package com.edukids.sdk

import com.edukids.sdk.model.EduMissionContract
import com.edukids.sdk.model.EduMissionFinishParams
import com.edukids.sdk.model.EduMissionStartParams
import java.util.concurrent.Future

interface EduMission {

    /**
     * Starts the mission. Sdk will mark the time of the function invocation
     * */
    suspend fun start(params: EduMissionStartParams): Result<EduMissionContract>

    /**
     * Ends the mission. Sdk will mark the time of the function invocation
     * */
    fun finish(params: EduMissionFinishParams)

    /** @see [start] */
    @Throws()
    fun startAsync(params: EduMissionStartParams): Future<EduMissionContract>

    /**
     * Lets the sdk know that user has completed all of the missions available.
     *
     * Calling this method notifies the launcher that the user has reached the END of the app and
     * the app can no longer provide _new_ or increasingly difficult tasks.
     *
     * If you want to "complete" your current mission, use [finish] instead and provide correct
     * params.
     *
     * @see [finish]
     */
    fun complete()

}

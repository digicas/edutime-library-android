package com.edukids.sdk.model

import android.os.Parcelable
import java.util.concurrent.Future

interface EduMission {

    /* Starts the mission. Sdk will mark the time of the function invocation */
    suspend fun start(params: EduMissionStartParams): EduMissionContract

    /* Ends the mission. Sdk will mark the time of the function invocation */
    fun finish(params: EduMissionFinishParams)

    /* @see [start] */
    @Throws()
    fun startAsync(params: EduMissionStartParams): Future<EduMissionContract>

    /*
     * Lets the sdk know that user has completed all of the missions available.
     */
    fun complete()

}

interface EduMissionStartParams : Parcelable {

    /* Notifies the sdk that the task is going to be restarted, possibly subtracting points from the total or adding smaller bonus amount */
    val isRetry: Boolean

    /*
     * Skill description. Conforms to no particular pattern.
     * (eg. addition single numbers over 10) */
    val skills: List<String>

    /*
     * List of disciplines that this particular excercise requires.
     * (eg. math, english, ...) */
    val disciplines: List<String>?

    /* Permits app to send the sdk additional data to accompany the "start" entry */
    val dataBundle: Any?

    /*
     * States exact task type which is being performed.
     * (eg. math pyramid)
     */
    val eduTaskType: String

}

interface EduMissionFinishParams : Parcelable {

    /* Tells the SDK which contract does it want to end. This parameter is received from the result of `start(...)`*/
    val contractId: String

    /* Notifies the sdk that the mission was successful, so the underlying sdk knows that no further excercises are necessary */
    val isSuccess: Boolean

    /* Reports number of points acquired by completing the excercise. Also known as Edupoints */
    val pointsAcquired: Int

    /* Permits app the send the sdk additional data to accompany the "finish" entry */
    val dataBundle: Any?

}

interface EduMissionContract : Parcelable {

    /* Id of the created contract. */
    val id: String

}

interface MissionComplete : Parcelable
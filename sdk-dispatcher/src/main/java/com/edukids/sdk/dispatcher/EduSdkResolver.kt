package com.edukids.sdk.dispatcher

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.edukids.sdk.dispatcher.EduSdkResolver.toKey
import com.edukids.sdk.dispatcher.EduSdkResolver.toParcelables
import com.edukids.sdk.model.*

object EduSdkResolver {

    fun find() = EduModelResolver()
    fun dispatch() = EduTarget.Builder()

    internal fun build(target: EduTarget) = EduModelDispatcher(target)


    private enum class Key(
        val type: Class<out Parcelable>,
        val value: String
    ) {
        CURRENCY_STATS(CurrencyStats::class.java, ""),
        MISSION_START(EduMissionStartParams::class.java, ""),
        MISSION_FINISH(EduMissionFinishParams::class.java, ""),
        MISSION_RESPONSE(EduMissionContract::class.java, ""),
        MISSION_COMPLETE(TODO(), ""),
        SCREEN_TIME_CATEGORY(ScreenTimeCategory::class.java, ""),
        SCREEN_TIME_CATEGORY_CONSTRAINTS(ScreenTimeCategoryConstraints::class.java, ""),
        SCREEN_TIME_CATEGORY_SUGGEST(TODO(), ""),
        SKILL_LEVEL(SkillLevel::class.java, ""),
        TIME_CONSTRAINTS(TimeConstraints::class.java, "")
    }


    internal fun Parcelable.toKey() = Key.values()
        .first { it.type == this::class.java }
        .value

    internal fun Bundle.toParcelables() = Key.values()
        .asSequence()
        .filter { containsKey(it.value) }
        .mapNotNull { getParcelable<Parcelable>(it.value) }
        .toList()

}

class EduModelResolver internal constructor() {

    private val bundles = mutableListOf<Bundle>()

    fun inside(extras: Bundle) = apply {
        bundles.add(extras)
    }

    fun resolve() = bundles.flatMap { it.toParcelables() }

}

class EduModelDispatcher internal constructor(
    target: EduTarget
) {

    private val intent = target.toIntent()

    fun put(parcelable: Parcelable) = apply {
        intent.putExtra(parcelable.toKey(), parcelable)
    }

    fun dispatch(context: Context) = context.sendBroadcast(intent)

}
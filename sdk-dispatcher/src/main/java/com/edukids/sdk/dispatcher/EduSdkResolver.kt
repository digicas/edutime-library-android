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
        val key: String
    ) {
        CURRENCY_STATS(CurrencyStats::class.java, "T3QtvGWZR3"),
        MISSION_START(EduMissionStartParams::class.java, "EGK0lNmIbY"),
        MISSION_FINISH(EduMissionFinishParams::class.java, "2I60mPK2na"),
        MISSION_RESPONSE(EduMissionContract::class.java, "KAgXdCW1Oh"),
        MISSION_COMPLETE(MissionComplete::class.java, "TdgqDjXNlp"),
        SCREEN_TIME_CATEGORY(ScreenTimeCategory::class.java, "V6EhS20PFr"),
        SCREEN_TIME_CATEGORY_CONSTRAINTS(ScreenTimeCategoryConstraints::class.java, "omgFF3OU4C"),
        SCREEN_TIME_CATEGORY_SUGGEST(ScreenTimeCategorySuggestion::class.java, "sst3V1gdfE"),
        SKILL_LEVEL(SkillLevel::class.java, "iX7i2h9kB1"),
        TIME_CONSTRAINTS(TimeConstraints::class.java, "QKJgXPaHVD")
    }


    internal fun Parcelable.toKey() = Key.values()
        .first { it.type == this::class.java }
        .key

    internal fun Bundle.toParcelables() = Key.values()
        .asSequence()
        .filter { containsKey(it.key) }
        .mapNotNull { getParcelable<Parcelable>(it.key) }
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
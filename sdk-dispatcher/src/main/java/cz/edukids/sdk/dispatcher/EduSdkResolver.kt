package cz.edukids.sdk.dispatcher

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import cz.edukids.sdk.model.*
import cz.edukids.sdk.model.internal.InstanceKey

object EduSdkResolver {

    internal const val KEY_INSTANCE = "NvgGKdcJtu"

    private const val KEY_CURRENCY_STATS = "T3QtvGWZR3"
    private const val KEY_ERROR = "GM9dxyorpK"
    private const val KEY_MISSION_START = "EGK0lNmIbY"
    private const val KEY_MISSION_FINISH = "2I60mPK2na"
    private const val KEY_MISSION_CONTRACT = "KAgXdCW1Oh"
    private const val KEY_MISSION_COMPLETE = "TdgqDjXNlp"
    private const val KEY_CATEGORY = "V6EhS20PFr"
    private const val KEY_CATEGORY_CONSTRAINTS = "omgFF3OU4C"
    private const val KEY_CATEGORY_SUGGESTION = "sst3V1gdfE"
    private const val KEY_SKILL_LEVEL = "iX7i2h9kB1"
    private const val KEY_TIME_CONSTRAINTS = "QKJgXPaHVD"
    private const val KEY_TASK_ORDER = "YZje0gOBJH"

    private val keys = mapOf(
        CurrencyStats::class.java to KEY_CURRENCY_STATS,
        EduError::class.java to KEY_ERROR,
        EduMissionStartParams::class.java to KEY_MISSION_START,
        EduMissionFinishParams::class.java to KEY_MISSION_FINISH,
        EduMissionContract::class.java to KEY_MISSION_CONTRACT,
        EduMissionComplete::class.java to KEY_MISSION_COMPLETE,
        ScreenTimeCategory::class.java to KEY_CATEGORY,
        ScreenTimeCategoryInfo::class.java to KEY_CATEGORY_CONSTRAINTS,
        ScreenTimeCategorySuggestion::class.java to KEY_CATEGORY_SUGGESTION,
        SkillLevel::class.java to KEY_SKILL_LEVEL,
        TimeConstraints::class.java to KEY_TIME_CONSTRAINTS,
        EduTaskOrder::class.java to KEY_TASK_ORDER
    )


    fun find() = EduModelResolver()
    fun dispatch(instanceKey: InstanceKey? = null) = EduTarget.Builder(instanceKey)

    fun Intent.apply(instanceKey: InstanceKey?) = putExtra(KEY_INSTANCE, instanceKey)


    internal fun build(
        target: EduTarget,
        instanceKey: InstanceKey?
    ) = EduModelDispatcher(target, instanceKey)


    internal fun Parcelable.toKey() =
        this::class.java.toKey()

    internal fun Class<out Parcelable>.toKey() = keys[this]
        ?: error("This parcelable ($this) cannot be resolved to any key. Check the definitions")

    internal fun Bundle.toParcelables() = keys.values
        .asSequence()
        .filter { containsKey(it) }
        .mapNotNull { getParcelable(it) }
        .toList()

}

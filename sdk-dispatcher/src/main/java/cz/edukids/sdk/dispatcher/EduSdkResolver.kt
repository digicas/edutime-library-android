package cz.edukids.sdk.dispatcher

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresPermission
import cz.edukids.sdk.dispatcher.EduSdkResolver.apply
import cz.edukids.sdk.dispatcher.EduSdkResolver.toKey
import cz.edukids.sdk.dispatcher.EduSdkResolver.toParcelables
import cz.edukids.sdk.model.*
import cz.edukids.sdk.model.internal.EduConstants
import cz.edukids.sdk.model.internal.InstanceKey
import cz.edukids.sdk.model.permission.hasEduPermission

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
        ScreenTimeCategoryConstraints::class.java to KEY_CATEGORY_CONSTRAINTS,
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
        .mapNotNull { getParcelable<Parcelable>(it) }
        .toList()

}

class EduModelResolver internal constructor() {

    private val bundles = mutableListOf<Bundle>()

    fun inside(extras: Bundle) = apply {
        bundles.add(extras)
    }

    fun resolveInstance() = bundles.asSequence()
        .filter { it.containsKey(EduSdkResolver.KEY_INSTANCE) }
        .mapNotNull { it.getParcelable<InstanceKey>(EduSdkResolver.KEY_INSTANCE) }
        .firstOrNull()

    fun resolve() = bundles.flatMap { it.toParcelables() }

}

class EduModelDispatcher internal constructor(
    target: EduTarget,
    instanceKey: InstanceKey? = null
) {

    private val intent = target.toIntent().apply(instanceKey)

    fun put(parcelable: Parcelable) = apply {
        intent.putExtra(parcelable.toKey(), parcelable)
    }

    private fun isPermitted(context: Context) =
        context.hasEduPermission()

    private fun isInstalled(context: Context) =
        context.packageManager.queryBroadcastReceivers(intent, 0).isNotEmpty()

    @RequiresPermission(EduConstants.Permission.ACCESS_DATA)
    @Throws(SecurityException::class, IllegalStateException::class)
    fun dispatch(context: Context) {
        if (!isPermitted(context)) {
            throw SecurityException("Communication between SDK and host requires permission (${EduConstants.Permission.ACCESS_DATA})")
        }
        if (!isInstalled(context)) {
            throw IllegalStateException("App is not installed")
        }
        context.sendBroadcast(intent, EduConstants.Permission.ACCESS_DATA)
    }

}
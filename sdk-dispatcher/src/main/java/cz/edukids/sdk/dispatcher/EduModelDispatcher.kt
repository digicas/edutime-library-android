package cz.edukids.sdk.dispatcher

import android.content.Context
import android.os.Parcelable
import androidx.annotation.RequiresPermission
import cz.edukids.sdk.dispatcher.EduSdkResolver.apply
import cz.edukids.sdk.dispatcher.EduSdkResolver.toKey
import cz.edukids.sdk.model.internal.EduConstants
import cz.edukids.sdk.model.internal.InstanceKey
import cz.edukids.sdk.model.permission.hasEduPermission

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
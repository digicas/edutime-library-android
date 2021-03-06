package cz.edukids.sdk.dispatcher

import android.content.Intent
import cz.edukids.sdk.model.internal.InstanceKey

class EduTarget private constructor(
    internal val action: String,
    internal val packageName: String,
    internal val targetClass: String
) {

    internal fun toIntent() = Intent(action)
        .setClassName(packageName, targetClass)

    class Builder(
        private val instanceKey: InstanceKey? = null
    ) {

        private lateinit var action: String
        private lateinit var packageName: String
        private lateinit var targetClass: String

        fun setAction(action: String) = apply {
            this.action = action
        }

        fun setPackageName(packageName: String) = apply {
            this.packageName = packageName
        }

        fun setTargetClass(targetClass: String) = apply {
            this.targetClass = targetClass
        }

        fun createDispatcher() = EduTarget(
            action = action,
            packageName = packageName,
            targetClass = targetClass
        ).let { EduSdkResolver.build(it, instanceKey) }

    }

}
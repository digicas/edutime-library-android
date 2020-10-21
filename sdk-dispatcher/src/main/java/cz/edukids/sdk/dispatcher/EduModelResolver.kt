package cz.edukids.sdk.dispatcher

import android.os.Bundle
import cz.edukids.sdk.dispatcher.EduSdkResolver.toParcelables
import cz.edukids.sdk.model.internal.InstanceKey

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
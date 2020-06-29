package com.edukids.sdk.comms

import android.os.Parcelable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlin.reflect.KClass

@OptIn(ExperimentalCoroutinesApi::class)
internal class SuspendingWaitRegistry {

    private val contentRegistry = mutableMapOf<KClass<*>, Channel<Parcelable>>()


    suspend inline fun <reified T : Parcelable> await() = await(T::class)

    suspend fun <T : Parcelable> await(cls: KClass<T>): T {
        return getChannel(cls).receive() as T
    }


    fun push(element: Parcelable) {
        getChannel(element::class).offer(element)
    }


    private fun <T : Parcelable> getChannel(cls: KClass<T>) = contentRegistry.getOrPut(cls) {
        Channel<Parcelable>(Channel.RENDEZVOUS).also {
            it.invokeOnClose {
                contentRegistry.remove(cls)
            }
        }
    }

}
package com.edukids.sdk.comms

import android.os.Parcelable
import com.edukids.sdk.model.EduError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel

@OptIn(ExperimentalCoroutinesApi::class)
internal class SuspendingWaitRegistry {

    private val contentRegistry = mutableMapOf<Class<*>, Channel<Parcelable>>()


    @Throws(EduError::class)
    suspend inline fun <reified T : Parcelable> await() = await(T::class.java)

    @Throws(EduError::class)
    suspend fun <T : Parcelable> await(cls: Class<T>): T {
        val channel = getChannel(cls)
        val received = channel.receive()
        try {
            return when {
                cls.isInstance(received) -> received as T
                received is EduError -> throw received
                else -> throw IllegalStateException("Received an object that was unexpected")
            }
        } finally {
            channel.close()
        }
    }


    fun push(element: Parcelable) {
        val targetClass = if (element is EduError) {
            element.target
        } else {
            element::class.java
        }
        getChannel(targetClass).offer(element)
    }


    private fun <T : Parcelable> getChannel(cls: Class<T>) = contentRegistry.getOrPut(cls) {
        Channel<Parcelable>(Channel.RENDEZVOUS).also {
            it.invokeOnClose {
                contentRegistry.remove(cls)
            }
        }
    }

}
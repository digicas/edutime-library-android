package cz.edukids.sdk.async

import kotlinx.coroutines.*
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class CoroutineFuture<T : Any>(
    private val deferred: Deferred<T>,
    private val scope: CoroutineScope = MainScope(),
    context: CoroutineContext = Dispatchers.Default
) : Future<T>, CoroutineScope by scope {

    @Volatile
    private lateinit var value: T
    private val job = launch(context) {
        value = deferred.await()
    }

    override fun isDone(): Boolean = job.isCompleted

    override fun get(): T = runBlocking {
        suspendCancellableCoroutine<T> {
            job.invokeOnCompletion {
                value
            }
        }
    }

    override fun get(timeout: Long, unit: TimeUnit): T =
        throw UnsupportedOperationException("Use direct blocking call")

    override fun cancel(mayInterruptIfRunning: Boolean) = when {
        job.isCancelled -> false
        job.isActive -> if (mayInterruptIfRunning) {
            job.cancel()
            true
        } else {
            false
        }
        else -> {
            job.cancel()
            true
        }
    }

    override fun isCancelled() = job.isCancelled
}

fun <T : Any> Deferred<T>.asFuture(): Future<T> {
    return CoroutineFuture(this)
}

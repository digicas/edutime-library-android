package com.edukids.sdk.model.logging

import android.util.Log

interface SdkLogger {

    val level: Int

    fun log(level: Int, message: String, throwable: Throwable? = null)

    companion object {

        @Volatile
        private lateinit var logger: SdkLogger

        operator fun invoke(): SdkLogger {
            if (this::logger.isInitialized) {
                return logger
            }
            release()
            return logger
        }

        @JvmStatic
        fun debug() = level(Log.DEBUG)

        @JvmStatic
        fun release() = level(Log.ASSERT + 1)

        @JvmStatic
        fun level(level: Int) {
            logger = SdkLoggerImpl(level)
            v("Logger has been set to level $level")
        }

    }

}

fun SdkLogger.Companion.v(message: String, throwable: Throwable? = null) =
    invoke().log(Log.VERBOSE, message, throwable)

fun SdkLogger.Companion.d(message: String, throwable: Throwable? = null) =
    invoke().log(Log.DEBUG, message, throwable)

fun SdkLogger.Companion.i(message: String, throwable: Throwable? = null) =
    invoke().log(Log.INFO, message, throwable)

fun SdkLogger.Companion.w(message: String, throwable: Throwable? = null) =
    invoke().log(Log.WARN, message, throwable)

fun SdkLogger.Companion.e(message: String, throwable: Throwable? = null) =
    invoke().log(Log.ERROR, message, throwable)

private class SdkLoggerImpl(override val level: Int) : SdkLogger {

    override fun log(level: Int, message: String, throwable: Throwable?) {
        if (level >= this.level) {
            Log.println(level, TAG, message)
            if (throwable != null) {
                Log.println(level, TAG, Log.getStackTraceString(throwable))
            }
        }
    }

    companion object {
        private const val TAG = "EduSdk"
    }

}

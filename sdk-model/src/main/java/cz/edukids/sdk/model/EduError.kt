package cz.edukids.sdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EduError(
    val code: Int,
    /**
     * Does not provide information that should be displayed to the user, quite the contrary. Keep
     * these messages to developer builds only, implement [code] based maps with your own messages.
     * */
    override val message: String,
    val target: Class<out Parcelable>
) : RuntimeException(), Parcelable {

    companion object {

        /**
         * This error has unknown origin. Report use-case to the developer in order to get more
         * information.
         * */
        const val ERR_UNKNOWN = -1

        /**
         * No more coins can be supplied hence no additional mission can be started at this time.
         * Check with [cz.edukids.sdk.EduSdkInstance.getCurrencyStats] to prevent this error.
         * */
        const val ERR_COIN_BUDGET = 1

        /**
         * You cannot mock your own package name or instance ids.
         * */
        const val ERR_SECURITY = 2

        /**
         * Instance you've given to the sdk is invalid and does not exist. By violating this rule
         * multiple times you'll be revoked the communication with the sdk.
         * */
        const val ERR_INVALID_INSTANCE = 3

        /**
         * Mission contract does not exist or is invalid.
         * */
        const val ERR_INVALID_CONTRACT = 4

    }

}
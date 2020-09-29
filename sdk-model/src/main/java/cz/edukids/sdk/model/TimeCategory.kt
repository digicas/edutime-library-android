package cz.edukids.sdk.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
enum class TimeCategory(val id: String) : Parcelable {
    BASIC("BASIC"),
    EDU("EDU"),
    CREATIVE("CREATE"),
    GROWTH("GROWTH"),
    CONSUMPTION("CONSUME"),
    DISABLED("OFF");

    val priority
        get() =
            if (this == DISABLED) -1
            else ordinal

    companion object {

        fun String.asTimeCategory() = values().first { it.id == this }

    }
}
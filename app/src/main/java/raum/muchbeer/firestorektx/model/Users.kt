package raum.muchbeer.firestorektx.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(

    val displayName: String = "",
    val emojis:String="",
    val tokens: String="",
    val user_id: String=""
) : Parcelable {

}

package raum.muchbeer.firestorektx.model

import java.sql.Timestamp

data class Messages(
    val messages: String = "",
    val name : String= "",
    val timestamp: String = "",
    val user_id : String = "",
)

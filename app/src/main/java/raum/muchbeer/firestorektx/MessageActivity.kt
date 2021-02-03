package raum.muchbeer.firestorektx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_message.*
import raum.muchbeer.firestorektx.adaapter.MessageFireAdapter
import raum.muchbeer.firestorektx.adaapter.UsersFireAdapter
import raum.muchbeer.firestorektx.model.Messages
import raum.muchbeer.firestorektx.model.Users

class MessageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        auth = Firebase.auth

        val query = db.collection("chat")
        val option = FirestoreRecyclerOptions.Builder<Messages>().setQuery(query, Messages::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter = MessageFireAdapter(option)
        recycler_gchat.adapter = adapter
        recycler_gchat.layoutManager = LinearLayoutManager(this)


        val user = intent.getParcelableExtra<Users>(MainActivity.USERS_PARCEL)

        Log.d("MessageActivity", "The name of the user is : ${user.displayName}")
        Log.d("MessageActivity", "The user ID is  : ${user.user_id}")

        button_chat_send.setOnClickListener {
            if (!edit_chat_message.text.isNullOrEmpty()) {
                createNewMessage(edit_chat_message.text.toString(), user.displayName, user.user_id)
                getChatMessage()

            }
        }
    }

    private fun getChatMessage() {
        
    }

    private fun createNewMessage(message: String, name : String, user_id: String) {
      //  messages, name, timestamp, user_id
        val message  = Messages(message, name, "", user_id)
       // val docID = db.collection("chat").document().id
        db.collection("chat").document().set(message)
    }
}
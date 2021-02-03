package raum.muchbeer.firestorektx

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.data.model.User
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firestore.v1.FirestoreGrpc
import kotlinx.android.synthetic.main.activity_main.*
import raum.muchbeer.firestorektx.adaapter.UsersFireAdapter
import raum.muchbeer.firestorektx.model.Users
import raum.muchbeer.firestorektx.utility.EmojiFilter
import raum.muchbeer.firestorektx.utility.MyFirebaseMessageService.Companion.LOG_TAG

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val query = db.collection("usersliveband")
        initFCM()
        val option = FirestoreRecyclerOptions.Builder<Users>().setQuery(query, Users::class.java)
            .setLifecycleOwner(this)
            .build()

        val adapter = UsersFireAdapter(option, {selectItem:Users->selectedItem(selectItem)})
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
       // creatingUser()
    }


    private fun selectedItem(users: Users) {
        Toast.makeText(this, "Selected student : ${users.displayName}", Toast.LENGTH_LONG).show()

        val intent = Intent(this, MessageActivity::class.java)
        intent.putExtra(USERS_PARCEL, users)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val editText = EditText(this)


        if (item.itemId == R.id.log_out) {
            Log.d(TAG, "Log out")
            Firebase.auth.signOut()
            val logoutIntent = Intent(this, LoginActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutIntent)
        } else if (item.itemId == R.id.edit_text) {
            val dialog = AlertDialog.Builder(this)
                .setTitle("Edit your emojis")
                .setView(editText)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", null)
                .show()

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                Log.d(TAG, "Clicked on positive button")
                val textEntered = editText.text.toString()
                val currentUser = auth.currentUser

                val lengthFilter = InputFilter.LengthFilter(10)
                val emojiFilter = EmojiFilter()

                editText.filters = arrayOf<InputFilter>(lengthFilter)

                if(textEntered.isBlank()) {
                    Toast.makeText(this, "No emoji entered", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                 if (currentUser==null) {
                     Toast.makeText(this, "No user found please login", Toast.LENGTH_LONG).show()
                     return@setOnClickListener
                }
                else { Firebase.firestore.collection("usersliveband")
                       .document(currentUser.uid)
                       .update("emojis", textEntered)
                        dialog.dismiss()
                 }
            }
        }
        return true
    }

    fun initFCM() {
      FirebaseMessaging.getInstance().token.addOnSuccessListener {
         sendRegistrationToServer(it)
          Log.d(LOG_TAG , "  NEW_TOKEN IS: ${it}");

      }
    }

    private fun sendRegistrationToServer(it: String?) {
        val docID = auth.currentUser?.uid


        val updateUser = mapOf(
            "tokens" to it,
            "user_id" to docID
        )

        val updateTokenRef = db.collection("usersliveband")
            .document(docID.toString())

        updateTokenRef.update(updateUser).addOnSuccessListener {
            Log.d(LOG_TAG, "USER excess data is successfull")
        }.addOnFailureListener {
            Log.d(LOG_TAG, "USER error found ${it.message}")
        }

    }


    fun creatingUser() {
        // Access a Cloud Firestore instance from your Activity
        val db = Firebase.firestore

        // Create a new user with a first and last name
        val user = hashMapOf(
                "first" to "Ada",
                "last" to "Lovelace",
                "born" to 1815
        )

        // Add a new document with a generated ID
        db.collection("school")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("Activity", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Activity", "Error adding document", e)
                }


    }

     companion object {
        private const val TAG = "MainActivity"
        const val USERS_PARCEL= "user_parcel"
    }
}
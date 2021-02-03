package raum.muchbeer.firestorektx.adaapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.user_item.view.*
import raum.muchbeer.firestorektx.R
import raum.muchbeer.firestorektx.model.Users

class UsersFireAdapter( firestoreopiton : FirestoreRecyclerOptions<Users>,
                          private val selectedItem: (Users)->Unit) : FirestoreRecyclerAdapter<Users,
        UsersFireAdapter.UsersViewHolder>(firestoreopiton) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsersFireAdapter.UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewUser: View = inflater.inflate(R.layout.user_item, parent, false)

        return UsersViewHolder(viewUser)
    }


    override fun onBindViewHolder(holder: UsersViewHolder, position: Int, model: Users) {
        holder.bind(model, selectedItem)
    }

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         fun bind(user: Users, selectedItem: (Users)->Unit) {
            itemView.emojis.text = user.emojis
            itemView.displayName.text = user.displayName
             itemView.setOnClickListener {
                 selectedItem(user)
             }
        }
    }
}
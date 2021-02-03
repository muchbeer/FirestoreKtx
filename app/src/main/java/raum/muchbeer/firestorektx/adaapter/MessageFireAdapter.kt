package raum.muchbeer.firestorektx.adaapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.chat_messages_item.view.*
import raum.muchbeer.firestorektx.R
import raum.muchbeer.firestorektx.model.Messages

class MessageFireAdapter(fireOption: FirestoreRecyclerOptions<Messages>) :
    FirestoreRecyclerAdapter<Messages, MessageFireAdapter.MessageVH>(fireOption) {

          override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageVH {
        val layoutInflater = LayoutInflater.from(parent.context)
              val vH = layoutInflater.inflate(R.layout.chat_messages_item, parent, false)

              return MessageVH(vH)

    }

    override fun onBindViewHolder(holder: MessageVH, position: Int, model: Messages) {
       holder.bind(model)
    }

    class MessageVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(message: Messages) {
                itemView.text_gchat_user_other.text = message.name
                itemView.text_gchat_message_other.text = message.messages
                itemView.text_gchat_timestamp_other.text = message.timestamp
            }
    }
}
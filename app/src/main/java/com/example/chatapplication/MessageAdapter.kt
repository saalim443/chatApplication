package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context,val  messagelist:ArrayList<MessageResponse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_RECEIVE=1
    val ITEM_SENT=2

    class SentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val sentmessage=itemView.findViewById<TextView>(R.id.txt_sent_message)
    }

    class ReceiveViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val receivemessage=itemView.findViewById<TextView>(R.id.txt_receive_message)
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//    if(viewType==1)
//    {
//        val view:View=LayoutInflater.from(context).inflate(R.layout.receivemessage,parent,false)
//        return  ReceiveViewHolder(view)
//    }
//        else
//    {
//        val view:View=LayoutInflater.from(context).inflate(R.layout.sentmessage,parent,false)
//        return  ReceiveViewHolder(view)
//    }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_RECEIVE) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.receivemessage, parent, false)
            ReceiveViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.sentmessage, parent, false)
            SentViewHolder(view)
        }
    }


    override fun getItemCount(): Int {
       return messagelist.size
    }


    override fun getItemViewType(position: Int): Int {
        val currentMessage= messagelist[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmessage= messagelist[position]
       if(holder.javaClass ==SentViewHolder::class.java)
       {

        val viewHolder= holder as SentViewHolder
           holder.sentmessage.text=currentmessage.message
       }
        else
       {

           val viewHolder= holder as ReceiveViewHolder
           holder.receivemessage.text=currentmessage.message
       }
    }

}

data class MessageResponse(
    var message: String = "",
    var senderId: String = ""
)
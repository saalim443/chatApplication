package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatapplication.databinding.ActivityChatStartUiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatStartUi : AppCompatActivity() {

    lateinit var binding: ActivityChatStartUiBinding
    lateinit var messageAdapter: MessageAdapter
    lateinit var messageList: ArrayList<MessageResponse>
lateinit var    messageResponse :MessageResponse
    var receiverRoom:String? = null
    var senderRoom:String? = null
    var   senderUid:String? = null

    lateinit var dbref:DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChatStartUiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        messageResponse=MessageResponse()

        var name =intent.getStringExtra("name")
        var receiverUid=intent.getStringExtra("uid")
        Toast.makeText(applicationContext, ""+name +"  "+ receiverUid, Toast.LENGTH_SHORT).show()
         senderUid=FirebaseAuth.getInstance().currentUser?.uid
        dbref=FirebaseDatabase.getInstance().getReference()



        senderRoom=receiverUid+senderUid
        receiverRoom=senderUid+receiverUid


        messageList=ArrayList()
        messageAdapter= MessageAdapter(this,messageList)
        binding.recyclerView.adapter=messageAdapter



        dbref.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object : ValueEventListener
        {

            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (post in snapshot.children) {
                    val message = post.getValue(MessageResponse::class.java)
                    message?.let { messageList.add(it) }
                }
                messageAdapter.notifyDataSetChanged() // Notify the adapter of data changes
            }


            override fun onCancelled(error: DatabaseError) {

            }

        })


        binding.imgSendImg.setOnClickListener {
            messageResponse.message=binding.edTypemsg.text.toString()
            messageResponse.senderId= senderUid.toString()

            dbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageResponse).addOnSuccessListener {
                    dbref.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageResponse)

                    // Clear the EditText
                    binding.edTypemsg.text!!.clear()

                    // Clear focus from the EditText
                    binding.edTypemsg.clearFocus()
                }




        }








    }


}

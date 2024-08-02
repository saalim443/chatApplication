package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.databinding.ItemLayoutChatBinding

data class User(
    val name: String = "",
    val email: String = "",
    val uid: String = ""
)

class UserAdapter(private val context: Context, private val userList: MutableList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(private val binding: ItemLayoutChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvName.text = user.name
//            binding.tvTitle.text = user.email

            binding.tvName.setOnClickListener {
                val intent= Intent(context,ChatStartUi::class.java)
                intent.putExtra("name",user.name)
                intent.putExtra("uid",user.uid)
                context.startActivity(intent)

            }
        }
    }
}


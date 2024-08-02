package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chatapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var userAdapter: UserAdapter
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var dbref: DatabaseReference
    lateinit var userList: MutableList<User> // Changed to MutableList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // Use the binding object instead of R.layout.activity_main

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()
        dbref = FirebaseDatabase.getInstance().getReference()
        userList = mutableListOf() // Initialize as mutable list
        userAdapter = UserAdapter(this, userList)

        binding.recyclerView.adapter = userAdapter // Set the adapter to RecyclerView

        dbref.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear() // Clear the list before adding new data
                for (data in snapshot.children) {
                    val currentuser = data.getValue(User::class.java)
                    if (currentuser != null) {
                        userList.add(currentuser) // Add new user data
                    }
                }
                userAdapter.notifyDataSetChanged() // Notify adapter of data changes
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle potential errors
            }
        })
    }

    fun gotonextAddfriend(view: View) {
        startActivity( Intent(this@MainActivity,RegisterUi::class.java))


    }
}

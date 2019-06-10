package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.MuseumQuests.QuestInfo.Companion.id_current
import com.example.MuseumQuests.QuestInfo.Companion.username_current
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_start.*

class Start : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        buttonStart.setOnClickListener{
            //saveData()
            id_current = -1
            val intent = Intent(this, Home::class.java)
            username_current = username.text.toString()
            findPerson(0, username_current, intent)
        }


    }

    fun findPerson (i : Int, username : String, intent : Intent) {
        val rootRef = FirebaseDatabase.getInstance().getReference("people/$i/username")
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java).toString()
                if (post != "null") {
                    if (post == username) {
                        id_current = i
                        startActivity(intent)
                    } else
                        findPerson(i + 1, username_current, intent)
                }
            }
        })
    }
}
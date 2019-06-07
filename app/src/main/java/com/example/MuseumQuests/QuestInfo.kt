package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_quest_info.*

class QuestInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_info)

        var rootRef = FirebaseDatabase.getInstance().getReference("museums/quests/quest 1/description")
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                textView.text = post
            }
        })

        rootRef = FirebaseDatabase.getInstance().getReference("museums/quests/quest 1/title")
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                text_questname.text = post
            }
        })

        //Переход к вопросам, т.е. к самому квесту
            button_start.setOnClickListener {
                val intent = Intent(this, Question::class.java)

                startActivity(intent)
            }

    }
}



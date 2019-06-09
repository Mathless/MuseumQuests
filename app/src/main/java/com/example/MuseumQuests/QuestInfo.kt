package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_quest_info.*

class QuestInfo : AppCompatActivity() {

    companion object {
        const val KEY_QUEST = "key_to_quest"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_info)
        val i = intent.getIntExtra(KEY_QUEST, 0)
        setValByPath("museums/quests/$i/description", textView)
        setValByPath("museums/quests/$i/title", text_questname)

        //printAll(0)
        //Переход к вопросам, т.е. к самому квесту
            button_start.setOnClickListener {
                val intent = Intent(this, Question::class.java)
                intent.putExtra(KEY_QUEST, i)
                startActivity(intent)
            }

    }
}





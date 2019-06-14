package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_quest_info.*
import kotlinx.android.synthetic.main.activity_quest_list.*

class QuestInfo : AppCompatActivity() {

    companion object {
        const val KEY_QUEST_NUM = "key_to_quest"
        const val KEY_QUESTION_NUM = "key_to_question"
        var username_current :String = ""
        var id_current : Int = -1
        var points_current : Int = -1
        var totalPoints = 0
        var maxPoints = 0
        var questTitle = " "
        var questTitle_en = " "
        var language = "default"
        var pathQuests : String = "quests"
        var criteria = true
        var quests_lang = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_info)
        val i = intent.getIntExtra(KEY_QUEST_NUM, 0)
        setValByPath("museums/$pathQuests/$i/description", textView)
        setValByPath("museums/$pathQuests/$i/title", text_questname)

        var rootRef = FirebaseDatabase.getInstance().getReference("museums/quests/$i/title")
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java).toString()
                questTitle = post
            }
        })
        rootRef = FirebaseDatabase.getInstance().getReference("museums/quests-en/$i/title")
        rootRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java).toString()
                questTitle_en = post
            }
        })


        //printAll(0)
        //Переход к вопросам, т.е. к самому квесту
            button_start.setOnClickListener {
                maxPoints = 0
                val intent = Intent(this, Question::class.java)
                intent.putExtra(KEY_QUEST_NUM, i)
                intent.putExtra(KEY_QUESTION_NUM, 0)
                startActivity(intent)
            }

    }
}





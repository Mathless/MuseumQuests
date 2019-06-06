package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_quest_info.*

class QuestInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_info)


        //Переход к вопросам, т.е. к самому квесту
            button_start.setOnClickListener {
                val intent = Intent(this, Question::class.java)

                startActivity(intent)
            }

    }
}

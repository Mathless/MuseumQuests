package com.example.MuseumQuests

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_new_quest.*

class NewQuestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_quest)

        button_makequestion.setOnClickListener {
            val intent = Intent(this, NewQuestionActivity::class.java)
            startActivity(intent)
        }
    }

}

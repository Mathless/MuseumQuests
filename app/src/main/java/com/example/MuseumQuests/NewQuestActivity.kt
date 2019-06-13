package com.example.MuseumQuests

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import com.example.MuseumQuests.Home.Companion.k
import com.example.MuseumQuests.Home.Companion.new_quest_description
import com.example.MuseumQuests.Home.Companion.new_quest_title

import kotlinx.android.synthetic.main.activity_new_quest.*
import kotlinx.android.synthetic.main.activity_new_question.*

class NewQuestActivity : AppCompatActivity() {

    override fun onBackPressed() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_quest)

        if (!QuestInfo.criteria) {
            newquesttitle.hint = new_quest_title
            newquestdeskription.hint = new_quest_description
        }

        button_makequestion.setOnClickListener {
            if (newquesttitle.text.toString() != "") {
                new_quest_title = newquesttitle.text.toString()
                new_quest_description = newquestdeskription.text.toString()
                k = 0
                val intent = Intent(this, NewQuestionActivity::class.java)
                startActivity(intent)
            }
            else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage("Input quest title!")
                builder.setNeutralButton("OK"){_,_->}
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

}

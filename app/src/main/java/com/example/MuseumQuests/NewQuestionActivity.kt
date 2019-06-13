package com.example.MuseumQuests

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton
import android.widget.Toast
import com.example.MuseumQuests.Home.Companion.new_quest_description
import com.example.MuseumQuests.Home.Companion.new_quest_questions
import com.example.MuseumQuests.Home.Companion.new_quest_question
import com.example.MuseumQuests.Home.Companion.new_quest_title
import com.example.MuseumQuests.Home.Companion.size
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_new_question.*

class NewQuestionActivity : AppCompatActivity() {

    override fun onBackPressed() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_question)

        button_makequestion2.setOnClickListener {
            addQuestion()
            val intent = Intent(this, NewQuestionActivity::class.java)
            startActivity(intent)
        }
        button_done.setOnClickListener {
            setData(0)
            addQuestion()
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        button_blin.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Cancel creating")
            builder.setMessage("Are you sure you want to cancel creating?")
            builder.setPositiveButton("Delete quest"){_, _ ->
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }
            builder.setNeutralButton("Cancel"){_,_->}
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    fun setData(id : Int){
        val root = FirebaseDatabase.getInstance().getReference("museums/quests/$id/title")
        val root1 = FirebaseDatabase.getInstance().getReference("museums/quests/$id")
        root.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                if (post != null)
                    setData(id + 1)
                else {
                    println("GO $new_quest_title")

                    root1.child("title").setValue(new_quest_title).addOnCompleteListener {}
                    root1.child("description").setValue(new_quest_description).addOnCompleteListener {}

                    for (i in 0..size) {
                        if (new_quest_questions[i].question != "" && new_quest_questions[i].question != " ") {
                            for (j in 0..3)
                                root1.child("questions").child("$i").child("answers_options").child("$j").setValue(
                                    new_quest_questions[i].answer_options[j]
                                ).addOnCompleteListener {}
                            root1.child("questions").child("$i").child("correct_answer")
                                .setValue(new_quest_questions[i].correct_answer).addOnCompleteListener {}
                            root1.child("questions").child("$i").child("question")
                                .setValue(new_quest_questions[i].question).addOnCompleteListener {}
                        }
                    }
                }
            }

        })
    }


    fun addQuestion(){

        if (newquestion.text.toString() == " " || questionanswer1.text.toString() == " ")
            return

        var correct : String = ""

        when {
            radio_group.checkedRadioButtonId == 2131230784 -> correct = questionanswer1.text.toString()
            radio_group.checkedRadioButtonId == 2131230785 -> correct = questionanswer2.text.toString()
            radio_group.checkedRadioButtonId == 2131230786 -> correct = questionanswer3.text.toString()
            radio_group.checkedRadioButtonId == 2131230787 -> correct = questionanswer4.text.toString()
        }

        new_quest_questions[size].question = newquestion.text.toString()
        new_quest_questions[size].answer_options[0] = questionanswer1.text.toString()
        new_quest_questions[size].answer_options[1] = questionanswer2.text.toString()
        new_quest_questions[size].answer_options[2] = questionanswer3.text.toString()
        new_quest_questions[size].answer_options[3] = questionanswer4.text.toString()
        new_quest_questions[size].correct_answer = correct
        size++
    }

}

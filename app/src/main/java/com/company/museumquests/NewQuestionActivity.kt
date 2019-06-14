package com.company.museumquests

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.company.museumquests.Home.Companion.k
import com.company.museumquests.Home.Companion.new_quest_description
import com.company.museumquests.Home.Companion.new_quest_questions
import com.company.museumquests.Home.Companion.new_quest_question
import com.company.museumquests.Home.Companion.new_quest_questions2
import com.company.museumquests.Home.Companion.new_quest_title
import com.company.museumquests.Home.Companion.size
import com.company.museumquests.QuestInfo.Companion.criteria
import com.company.museumquests.QuestInfo.Companion.language
import com.company.museumquests.QuestInfo.Companion.quests_lang
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
            if (criteria)
                addQuestion(new_quest_questions)
            else addQuestion(new_quest_questions2)
            val intent = Intent(this, NewQuestionActivity::class.java)
            startActivity(intent)
        }
        button_done.setOnClickListener {
            if (criteria) {
                addQuestion(new_quest_questions)
                setData(0, new_quest_questions)
            }
            else {
                addQuestion(new_quest_questions2)
                setData(0, new_quest_questions2)
            }



            if (!criteria) {
                if (language == "en")
                    quests_lang = "quests"
                else quests_lang = "quests-en"
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }
            else {
                criteria = false
                val intent = Intent(this, NewQuestActivity::class.java)
                Toast.makeText(applicationContext,"Translate the quest now!", Toast.LENGTH_LONG).show()

                startActivity(intent)
            }
        }

        if (!criteria) button_blin.isEnabled = false
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

        if (!criteria){
            newquestion.hint = new_quest_questions[k].question
            questionanswer1.hint = new_quest_questions[k].answer_options[0]
            questionanswer2.hint = new_quest_questions[k].answer_options[1]
            questionanswer3.hint = new_quest_questions[k].answer_options[2]
            questionanswer4.hint = new_quest_questions[k].answer_options[3]
            when {
                new_quest_questions[k].correct_answer == new_quest_questions[k].answer_options[0] -> correctanswer1.isChecked = true
                new_quest_questions[k].correct_answer == new_quest_questions[k].answer_options[1] -> correctanswer2.isChecked = true
                new_quest_questions[k].correct_answer == new_quest_questions[k].answer_options[2] -> correctanswer3.isChecked = true
                new_quest_questions[k].correct_answer == new_quest_questions[k].answer_options[3] -> correctanswer4.isChecked = true
            }
            k++
        }

    }

    fun setData(id : Int, new_quest_questions : Array<new_quest_question>){
        val root = FirebaseDatabase.getInstance().getReference("museums/$quests_lang/$id/title")
        val root1 = FirebaseDatabase.getInstance().getReference("museums/$quests_lang/$id")
        root.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                if (post != null)
                    setData(id + 1, new_quest_questions)
                else {

                    root1.child("title").setValue(new_quest_title).addOnCompleteListener {}
                    root1.child("description").setValue(new_quest_description).addOnCompleteListener {}

                    println("SIZE: $size")

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
                    size = 0
                }
            }

        })
    }


    fun addQuestion(new_quest_questions : Array<new_quest_question>){

        if (newquestion.text.toString() == " " || questionanswer1.text.toString() == " ")
            return

        var correct : String = ""

        when {
            correctanswer1.isChecked -> correct = questionanswer1.text.toString()
            correctanswer2.isChecked -> correct = questionanswer2.text.toString()
            correctanswer3.isChecked -> correct = questionanswer3.text.toString()
            correctanswer4.isChecked -> correct = questionanswer4.text.toString()
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

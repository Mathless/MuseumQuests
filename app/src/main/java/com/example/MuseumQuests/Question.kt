package com.example.MuseumQuests

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_quest_list.*
import kotlinx.android.synthetic.main.activity_question.*
import org.w3c.dom.Text

class Question : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val i = intent.getIntExtra(QuestInfo.KEY_QUEST_NUM, 0)
        val j = intent.getIntExtra(QuestInfo.KEY_QUESTION_NUM, 0)
        /** //Сами вопросы !!!!!! пока что ничего не отображается - не знаю, что с этим делать
        val dataArray = Array<String>(3){i -> "${i + 1}. Quest #${i + 1}"}
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataArray)
        list_question.adapter = adapter */

        setValByPath("museums/quests/$i/questions/$j/question", text_question)
        setValByPath("museums/quests/$i/questions/$j/answers_options/0", text_option1)
        setValByPath("museums/quests/$i/questions/$j/answers_options/1", text_option2)

        //Проверка вопроса или переход на окно результата
        button_check.setOnClickListener{

            checkWhereToGo(i, j)
        }

        // Конпка скипа вопроса
        button_skip.setOnClickListener{
            checkWhereToGo(i, j)

        }
    }

    fun checkWhereToGo(i : Int, j : Int) {
        val rootRef = FirebaseDatabase.getInstance().getReference("museums/quests/$i/questions/${j + 1}/question")
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {

                val post = p0.getValue(String::class.java)

                val intent: Intent
                when (post == null) {
                    true -> intent = fun1()
                    false -> intent = fun2()
                }
                intent.putExtra(QuestInfo.KEY_QUEST_NUM, i)
                intent.putExtra(QuestInfo.KEY_QUESTION_NUM, j + 1)
                startActivity(intent)

            }
        })
    }
    fun fun1() : Intent {
        return Intent(this, Result::class.java)
    }
    fun fun2() : Intent {
        return Intent(this, Question::class.java)
    }

}


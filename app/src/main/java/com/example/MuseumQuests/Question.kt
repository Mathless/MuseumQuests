package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_quest_list.*
import kotlinx.android.synthetic.main.activity_question.*
import org.w3c.dom.Text

class Question : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val i = intent.getIntExtra(QuestInfo.KEY_QUEST, 0)
        /** //Сами вопросы !!!!!! пока что ничего не отображается - не знаю, что с этим делать
        val dataArray = Array<String>(3){i -> "${i + 1}. Quest #${i + 1}"}
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataArray)
        list_question.adapter = adapter */

        setValByPath("museums/quests/$i/questions/0/question", text_question)
        setValByPath("museums/quests/$i/questions/0/answers_options/0", text_option1)
        setValByPath("museums/quests/$i/questions/0/answers_options/1", text_option2)

        //Проверка вопроса или переход на окно результата
        button_check.setOnClickListener{

            // здесь должен быть if остались ли ещё вопросы else переход к результату

            val intent = Intent(this, Result::class.java)

            startActivity(intent)
        }

        // Конпка скипа вопроса
        button_skip.setOnClickListener{

            // здесь должен быть if остались ли ещё вопросы else переход к результату

            val intent = Intent(this, Result::class.java)

            startActivity(intent)
        }
    }
}
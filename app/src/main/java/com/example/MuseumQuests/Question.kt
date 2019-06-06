package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_question.*

class Question : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)


        //Сами вопросы !!!!!! пока что ничего не отображается - не знаю, что с этим делать
        val dataArray = Array<String>(3){i -> "${i + 1}. Quest #${i + 1}"}
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataArray)
        list_question.adapter = adapter


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

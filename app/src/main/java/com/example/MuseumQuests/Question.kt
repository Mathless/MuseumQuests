package com.example.MuseumQuests

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.MuseumQuests.QuestInfo.Companion.totalPoints
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_quest_info.*
import kotlinx.android.synthetic.main.activity_quest_list.*
import kotlinx.android.synthetic.main.activity_question.*
import org.w3c.dom.Text

class Question : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        var answerGiven : String = " "

        val i = intent.getIntExtra(QuestInfo.KEY_QUEST_NUM, 0)
        val j = intent.getIntExtra(QuestInfo.KEY_QUESTION_NUM, 0)

        setValByPath("museums/quests/$i/questions/$j/question", text_question) // заполнение поля с вопросом
        setValToListByPath("museums/quests/$i/questions/$j/answers_options/", 0, this) // заполнение листа с вариантами ответов

        text_list.setOnItemClickListener{
                _, view, _, _ ->
            val value = (view as TextView).text.toString()
            answerGiven = value
        }

        //Проверка вопроса или переход на окно результата
        button_check.setOnClickListener{
            checkAnswer(answerGiven, 10, "museums/quests/$i/questions/$j/correct_answer")
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

    val dataArray = Array<String>(4){_ -> "_"}

    fun setValToListByPath (path: String, i : Int, ctx : Context)
    {
        val rootRef = FirebaseDatabase.getInstance().getReference("$path$i")
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                if (post == null) {
                    val adapter = ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, dataArray)
                    text_list.adapter = adapter
                    return
                }
                dataArray[i] = post.toString()
                setValToListByPath (path, i + 1, ctx)
            }
        })
    }

    fun checkAnswer(answerGiven : String, points : Int, path : String) {
        val rootRef = FirebaseDatabase.getInstance().getReference(path)
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                if (post == answerGiven) {
                    totalPoints += points
                    println(totalPoints)
                }
            }
        })
    }


}

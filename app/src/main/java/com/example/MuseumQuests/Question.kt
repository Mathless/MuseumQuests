package com.example.MuseumQuests

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.MuseumQuests.QuestInfo.Companion.totalPoints
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_question.*
import org.w3c.dom.Text

class Question : AppCompatActivity() {

    override fun onBackPressed() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        var answerGiven : String = " "
        var idClicked : Int = 0
        var isChecked : Boolean = false

        val i = intent.getIntExtra(QuestInfo.KEY_QUEST_NUM, 0)
        val j = intent.getIntExtra(QuestInfo.KEY_QUESTION_NUM, 0)

        setValByPath("museums/quests/$i/questions/$j/question", text_question) // заполнение поля с вопросом
        setValToListByPath("museums/quests/$i/questions/$j/answers_options/", 0, this) // заполнение листа с вариантами ответов

        text_list.setOnItemClickListener{
                _, view, index, _ ->
            // все color.white плз
            if (!isChecked) {
                for (k in 0..3)
                    text_list.getChildAt(k).setBackgroundResource(R.drawable.rectangle)
                val value = (view as TextView).text.toString()
                view.setBackgroundResource(R.drawable.done_quests_list)
                answerGiven = value
                idClicked = index
            }
        }

        //Проверка вопроса или переход на окно результата
        button_check.setOnClickListener{
            if (isChecked)
                checkWhereToGo(i, j)
            else if (answerGiven == " ")
                for(k in 0..3)
                    text_list.getChildAt(k).setBackgroundResource(R.drawable.rectangle_warning)
            else {
                showRightAnswer("museums/quests/$i/questions/$j/correct_answer", text_list, idClicked)
                checkAnswer(answerGiven, 10, "museums/quests/$i/questions/$j/correct_answer")
                isChecked = true
                button_check.text = "Next"
                button_skip.setEnabled(false);
                button_skip.setBackgroundColor(0)

            }
        }

        // Конпка скипа вопроса
        button_skip.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Skip the question")
            builder.setMessage("You won't got any points!")
            builder.setPositiveButton("Skip"){_, _ ->
                checkWhereToGo(i, j)
            }
            builder.setNeutralButton("Cancel"){_,_->}
            val dialog: AlertDialog = builder.create()
            dialog.show()
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
                    text_list.adapter = adapter as ListAdapter
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

    fun showRightAnswer(path : String, text_list : ListView, idClicked : Int){
        val rootRef = FirebaseDatabase.getInstance().getReference(path)
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                for (k in 0..3) {
                    if ( (text_list.getChildAt(k) as TextView).text == post) {
                        text_list.getChildAt(idClicked).setBackgroundResource(R.drawable.rectangle_warning)
                        text_list.getChildAt(k).setBackgroundResource(R.drawable.rectangle_correct)
                    }
                }
            }
        })
    }

}

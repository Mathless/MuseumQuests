package com.example.MuseumQuests

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.example.MuseumQuests.QuestInfo.Companion.criteria
import com.example.MuseumQuests.QuestInfo.Companion.id_current
import com.example.MuseumQuests.QuestInfo.Companion.language
import com.example.MuseumQuests.QuestInfo.Companion.pathQuests
import com.example.MuseumQuests.QuestInfo.Companion.quests_lang
import com.example.MuseumQuests.QuestInfo.Companion.username_current
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_quest_list.*
import org.intellij.lang.annotations.Language
import java.util.*
import kotlin.collections.ArrayList

class Home : AppCompatActivity(){
    val logTag = "DEMO_TAG"
    var score = 0
    var place = 1
    val rootRef1 = FirebaseDatabase.getInstance().getReference("people/$id_current/language")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


                if (texthome.text == "HOME") {
                    language = "en"
                    pathQuests = "quests-en"
                    setValToListByPath("people/$id_current/quests_passed-en", 0, this@Home)
                } else {
                    language = "ru"
                    pathQuests = "quests"
                    setValToListByPath("people/$id_current/quests_passed", 0, this@Home)
                }


        //Кнопка смены языка
        button_changelanguage.setOnClickListener {
            if (language == "ru")
                language = "en"
            else language = "ru"

            rootRef1.setValue(language).addOnCompleteListener {}
            val locale = Locale(language)
            Locale.setDefault(locale)
            val configuration = Configuration()
            configuration.locale = locale
            baseContext.resources.updateConfiguration(configuration, null)

            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        checkScore()
        checkPlace(0)
        username.text = getString(R.string.usernam)+ " $username_current"

        Log.d(logTag, "onCreate called")
        gotoquestlist.setOnClickListener {
            val intent = Intent(this, QuestList::class.java)
            startActivity(intent)
        }
        signOut.setOnClickListener {
            //Диалоговое окно с потверждением выхода

            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.so))
            builder.setMessage(getString(R.string.arusur))
            builder.setPositiveButton(getString(R.string.so)){_, _ ->
                val intent = Intent(this, Start::class.java)
                startActivity(intent)
            }
            builder.setNeutralButton(getString(R.string.cancel)){_,_->}
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }
        createNew.setOnClickListener{
            criteria = true
            quests_lang = pathQuests
            chechPerm(this)
        }

    }

    fun checkScore(){
        val rootRef1 = FirebaseDatabase.getInstance().getReference("people/$id_current/pointsEarned")
        rootRef1.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java).toString()
                textscorehome.text = getString(R.string.score)+" $post"
                score = post.toInt()
            }
        })
    }

    fun checkPlace(id : Int){
        val rootRef1 = FirebaseDatabase.getInstance().getReference("people/$id/pointsEarned")
        rootRef1.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                if (post == null)
                    textplacehome.text = getString(R.string.place)+ " $place"
                else {
                    if (post.toInt() > score)
                        place++
                    checkPlace(id + 1)
                }
            }
        })
    }

    val dataArray = Array<String>(100000){_ -> "_"}

    fun setValToListByPath (path: String, i : Int, ctx : Context)
    {
        val rootRef = FirebaseDatabase.getInstance().getReference("$path/$i")
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                if (post == null) {
                    var dataArrayToGo = emptyArray<String>()

                    for(k in 0..99999)
                        if (dataArray[k] != "_") {
                            dataArrayToGo += dataArray[k]
                        }
                    val adapter = ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, dataArrayToGo)
                    listofquests.adapter = adapter as ListAdapter?
                    return
                }
                dataArray[i] = post.toString()
                println(dataArray[i])
                setValToListByPath (path, i + 1, ctx)
            }
        })
    }
    companion object {
        var size = 0
        var new_quest_title = "x"
        var new_quest_description = "x"
        class new_quest_question (
            question : String,
            correct_answer : String,
            answer_options : Array<String>
        )
        {
            var question = question
            var correct_answer = correct_answer
            var answer_options = answer_options
        }
        var new_quest_questions = Array<new_quest_question>(100){new_quest_question(" "," ", Array(4){" "})}
        var new_quest_questions2 = Array<new_quest_question>(100){new_quest_question(" "," ", Array(4){" "})}
        var k = 0
    }

    fun chechPerm(ctx : Context){
        val rootRef1 = FirebaseDatabase.getInstance().getReference("people/$id_current/moderator")
        rootRef1.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                if (post.toString() == "true") {
                    val intent = Intent(ctx, NewQuestActivity::class.java)
                    startActivity(intent)
                }
                else
                    Toast.makeText(getApplicationContext(),getString(R.string.donthavepermis), Toast.LENGTH_LONG).show()
            }
        })
    }

}

fun setValByPath (path: String, textWindowId : TextView)
{
    val rootRef = FirebaseDatabase.getInstance().getReference(path)
    rootRef.addValueEventListener(object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            println("not implemented")
        }
        override fun onDataChange(p0: DataSnapshot) {
            val post = p0.getValue(String::class.java)
            textWindowId.text = post
        }
    })

}





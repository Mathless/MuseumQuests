package com.company.museumquests

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.company.museumquests.QuestInfo.Companion.language
import com.company.museumquests.QuestInfo.Companion.totalPoints
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_quest_list.*

class QuestList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_list)

        if (language == "en")
            setValToListByPath("museums/quests-en/", 0, this, this) // заполнение листа с вариантами ответов
        else
            setValToListByPath("museums/quests/", 0, this, this)

        quest_list.setOnItemClickListener{
            _, view, index, _ ->
                val intent = Intent(this, QuestInfo::class.java)
                val value : Int = index
                intent.putExtra(QuestInfo.KEY_QUEST_NUM, value)
                totalPoints = 0
                startActivity(intent)
        }
    }

    val dataArray = Array<String>(100000){_ -> "_"}
    val dataArray1 = Array<String>(100000){_ -> "_"}

    fun setValToListByPath (path: String, i : Int, ctx : Context, act : Activity)
    {
        val rootRef = FirebaseDatabase.getInstance().getReference("$path$i")
        rootRef.child("title").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                if (post == null) {
                    var titles = emptyArray<String>()

                    for(k in 0..99999)
                        if (dataArray[k] != "_") {
                            titles += dataArray[k]
                        }


                    var descriptions = emptyArray<String>()

                    for(k in 0..99999)
                        if (dataArray1[k] != "_") {
                            descriptions += dataArray1[k]
                        }

                    val adapter = QuestListAdapter(act, titles, descriptions)
                    quest_list.adapter = adapter

                    return
                }
                rootRef.child("description").addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        println("not implemented")
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        val post1 = p0.getValue(String::class.java)



                        dataArray1[i] = post1.toString()
                        dataArray[i] = post.toString()
                        setValToListByPath (path, i + 1, ctx, act)


                    }
                })

            }
        })
    }
}

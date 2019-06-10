package com.example.MuseumQuests

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView
import com.example.MuseumQuests.QuestInfo.Companion.totalPoints
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_quest_list.*
import kotlinx.android.synthetic.main.activity_question.*

class QuestList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_list)

        setValToListByPath("museums/quests/", 0, this) // заполнение листа с вариантами ответов

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

    fun setValToListByPath (path: String, i : Int, ctx : Context)
    {
        val rootRef = FirebaseDatabase.getInstance().getReference("$path$i/title")
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
                    quest_list.adapter = adapter as ListAdapter?
                    return
                }
                dataArray[i] = post.toString()
                setValToListByPath (path, i + 1, ctx)
            }
        })
    }
}

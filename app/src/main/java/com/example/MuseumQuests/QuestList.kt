package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_quest_list.*

class QuestList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quest_list)
        val data = Array<String>(3){i -> "${i + 1}. Quest #${i + 1}"}
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        quest_list.adapter = adapter

        quest_list.setOnItemClickListener{
            _, view, _, _ ->
                val intent = Intent(this, QuestInfo::class.java)
                val value : Int = ((view as TextView).text.toString()[0] - 1).toInt() - 48
                intent.putExtra(QuestInfo.KEY_QUEST, value)
                startActivity(intent)
        }
    }
}

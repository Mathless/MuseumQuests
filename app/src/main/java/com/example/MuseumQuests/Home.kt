package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    val logTag = "DEMO_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Log.d(logTag, "onCreate called")
        gotoquestlist.setOnClickListener {
            val intent = Intent(this, QuestList::class.java)
            startActivity(intent)
        }

        val data = Array(5){i -> ("Quest" + "$i")}
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        listofquests.adapter = adapter as ListAdapter?

    }
}

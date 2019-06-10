package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.MuseumQuests.QuestInfo.Companion.id_current
import com.example.MuseumQuests.QuestInfo.Companion.totalPoints
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_result.*

class Result : AppCompatActivity() {

    val logTag = "DEMO_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        textscore.text = "Score : $totalPoints"

        Log.d(logTag, "onCreate called")
        gotohomefromresult.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)

        }
    }
}

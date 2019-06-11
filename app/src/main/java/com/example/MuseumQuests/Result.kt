package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.MuseumQuests.QuestInfo.Companion.id_current
import com.example.MuseumQuests.QuestInfo.Companion.questTitle
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

        setPassedQuest(0)
        setScore()

        Log.d(logTag, "onCreate called")
        gotohomefromresult.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)

        }
    }

    fun setScore(){
        val rootRef1 = FirebaseDatabase.getInstance().getReference("people/$id_current/pointsEarned")
        rootRef1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java).toString()
                var allPoints = totalPoints + post.toInt()
                val ref = FirebaseDatabase.getInstance().getReference("people/$id_current/pointsEarned")
                ref.setValue("$allPoints")
            }
        })
    }

    fun setPassedQuest(index : Int){
        val rootRef1 = FirebaseDatabase.getInstance().getReference("people/$id_current/quests_passed/$index")
        rootRef1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)

                if (post != null && post.toString() == questTitle)
                    totalPoints = 0

                if (post != null && post.toString() != questTitle)
                    setPassedQuest(index + 1)
                else {
                    rootRef1.setValue("$questTitle")
                }
            }
        })
    }

}

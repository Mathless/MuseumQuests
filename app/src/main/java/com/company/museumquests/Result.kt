package com.company.museumquests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.company.museumquests.QuestInfo.Companion.id_current
import com.company.museumquests.QuestInfo.Companion.maxPoints
import com.company.museumquests.QuestInfo.Companion.questTitle
import com.company.museumquests.QuestInfo.Companion.questTitle_en
import com.company.museumquests.QuestInfo.Companion.totalPoints
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
        textscore.text = getString(R.string.score11)+ ": $totalPoints / $maxPoints"

        setPassedQuest(0)

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
        val rootRef2 = FirebaseDatabase.getInstance().getReference("people/$id_current/quests_passed-en/$index")
        rootRef1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)

                if (post != null && post.toString() == questTitle) {
                    totalPoints = 0
                    Toast.makeText(applicationContext,getString(R.string.gotand), Toast.LENGTH_LONG).show()
                }

                if (post != null && post.toString() != questTitle)
                    setPassedQuest(index + 1)
                else {
                    setScore()
                    rootRef1.setValue(questTitle)
                    rootRef2.setValue(questTitle_en)
                }
            }
        })
    }

}

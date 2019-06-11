package com.example.MuseumQuests

import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.MuseumQuests.QuestInfo.Companion.id_current
import com.example.MuseumQuests.QuestInfo.Companion.points_current
import com.example.MuseumQuests.QuestInfo.Companion.username_current
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_start.*
import android.widget.RelativeLayout
import java.util.*

class Start : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Смена языка

//        val locale = Locale("ru")
//        Locale.setDefault(locale)
//        val configuration = Configuration()
//        configuration.locale = locale
//        baseContext.resources.updateConfiguration(configuration, null)









        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        buttonStart.setOnClickListener{
            //saveData()
            id_current = -1
            val intent = Intent(this, Home::class.java)
            username_current = username.text.toString()
            val password = password.text.toString()
            findPerson(0, username_current, password, intent) // check the input data
        }


        //Кнопка регистрации
        button_signup.setOnClickListener{
            val intent1 = Intent(this, Sign_up::class.java)
            startActivity(intent1)
        }
    }

    fun findPerson (i : Int, username : String, password : String, intent : Intent) {
        val rootRef = FirebaseDatabase.getInstance().getReference("people/$i/username")
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java).toString()
                if (post != "null") {
                    if (post == username) {
                        id_current = i

                        val rootRefPass = FirebaseDatabase.getInstance().getReference("people/$id_current/verification_code")
                        rootRefPass.addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(p1: DatabaseError) {
                                println("not implemented")
                            }
                            override fun onDataChange(p1: DataSnapshot) {
                                val post1 = p1.getValue(String::class.java).toString()
                                if (post1 == password)
                                    startActivity(intent)
                                else
                                    showToastWrongData()
                            }
                        })
                    } else
                        findPerson(i + 1, username_current, password, intent)
                }
                else
                    showToastWrongData()
            }
        })
    }

    fun showToastWrongData(){
        val toast : Toast = Toast.makeText(getApplicationContext(),
        "Wrong username or password!",
        Toast.LENGTH_SHORT)
        toast.show()
    }
}
package com.example.MuseumQuests

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.example.MuseumQuests.QuestInfo.Companion.id_current
import com.example.MuseumQuests.QuestInfo.Companion.points_current
import com.example.MuseumQuests.QuestInfo.Companion.username_current
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_start.*
import java.util.*
import android.text.InputType
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_start.username


class Start : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        //username.width = password.width
         //Смена языка

//        val locale = Locale("ru")
//        Locale.setDefault(locale)
//        val configuration = Configuration()
//        configuration.locale = locale
//        baseContext.resources.updateConfiguration(configuration, null)

        /*val sas = Sas(password)
        val det = GestureDetector(this, sas)
        password.setOnTouchListener { v, event ->
            det.onTouchEvent(event)
        }*/
        password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var ident = true

        glazyk.setOnClickListener {
            if (!ident)
                password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            else
                password.setInputType(InputType.TYPE_CLASS_TEXT)
            ident = !ident
            val et = findViewById<View>(R.id.password) as EditText
            et.setSelection(et.text.length)
        }

        buttonStart.setOnClickListener{
            //saveData()
            id_current = -1
            val intent = Intent(this, Home::class.java)
            username_current = username.text.toString().toLowerCase()
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
                                if (post1 == password) {

                                    val rootRef1 = FirebaseDatabase.getInstance().getReference("people/$id_current/language")
                                    rootRef1.addValueEventListener(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError) {
                                            println("not implemented")
                                        }
                                        override fun onDataChange(p0: DataSnapshot) {
                                            val post = p0.getValue(String::class.java)
                                            var post1 = post.toString()
                                            if (post == null)
                                                post1 = "default"
                                            QuestInfo.language = post1
                                            val locale = Locale(QuestInfo.language)
                                            Locale.setDefault(locale)
                                            val configuration = Configuration()
                                            configuration.locale = locale
                                            baseContext.resources.updateConfiguration(configuration, null)
                                            startActivity(intent)
                                        }
                                    })

                                }
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
        getString(R.string.wronguor),
        Toast.LENGTH_SHORT)
        toast.show()
    }



}
/*
class Sas(private val password: EditText) : GestureDetector.SimpleOnGestureListener() {
    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        super.onLongPress(e)
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}*/
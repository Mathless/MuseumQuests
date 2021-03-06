package com.company.museumquests

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.*
import com.company.museumquests.QuestInfo.Companion.id_current
import com.company.museumquests.QuestInfo.Companion.username_current
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_start.*
import java.util.*
import android.text.InputType
import android.util.Log
import android.widget.EditText
import com.company.museumquests.R
import com.company.museumquests.Sign_up
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
        password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var ident = true

        glazyk.setOnClickListener {
            if (!ident)
                password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            else
                password.inputType = InputType.TYPE_CLASS_TEXT
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
        val toast : Toast = Toast.makeText(
            applicationContext,
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

class DownloadImageFromInternet(internal var imageView: ImageView) :
    AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg urls: String): Bitmap? {
        val imageURL = urls[0]
        var bimage: Bitmap? = null
        try {
            val `in` = java.net.URL(imageURL).openStream()
            bimage = BitmapFactory.decodeStream(`in`)

        } catch (e: Exception) {
            Log.e("Error Message", e.message)
            e.printStackTrace()
        }

        return bimage
    }

    override fun onPostExecute(result: Bitmap) {
        if (result != null)
            imageView.setImageBitmap(result)
    }
}
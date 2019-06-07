package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_start.*

class Start : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        /**fun saveData() {

            val name = start_name_input.text.toString()

            val rootRef = FirebaseDatabase.getInstance().getReference("people_amount")

            rootRef.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {

                    var post = p0.getValue(String::class.java)
                    println(post)
                    /**val id = post?.toInt()?.plus(1).toString()

                    val person = Person(name)
                    val ref = FirebaseDatabase.getInstance().getReference("person")
                    ref.child(id).setValue(person).addOnCompleteListener {
                        Toast.makeText(applicationContext, "Added", Toast.LENGTH_SHORT).show()
                    }*/
                }

            })
            /**var id = "5"

            while (true) {

                val rootRef = FirebaseDatabase.getInstance().getReference("person/$id/name")
                rootRef.addValueEventListener(object : ValueEventListener {

                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val post = p0.getValue(String::class.java)
                        println(post)

                        if (post == null) {

                            println("id is: $id")

                            val person = Person(name)
                            ref.child(id).setValue(person).addOnCompleteListener {
                                Toast.makeText(applicationContext, "Added", Toast.LENGTH_SHORT).show()
                            }

                            return
                        }
                        else {
                            id = (id.toInt() + 1).toString()
                            println(id)
                        }
                    }
                })*/

        }*/


        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels
        start_title.width = width / 2

        buttonStart.setOnClickListener{
            //saveData()
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }


    }
}
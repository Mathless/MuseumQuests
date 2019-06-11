package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_sign_up.*

class Sign_up : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        button_signup.setOnClickListener {
            var name = username.text.toString()
            var password = password_signup1.text

            when {
                password_signup1.text != password_signup2.text -> {
                    val toast: Toast = Toast.makeText(
                        getApplicationContext(),
                        "Passwords don't match",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
                name == null -> {
                    val toast: Toast = Toast.makeText(
                        getApplicationContext(),
                        "Passwords don't match",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
                else -> {
                    checkUsername(name, 0)


                }
            }
        }
        button_signin_back.setOnClickListener{
            val intent1 = Intent(this, Start::class.java)
            startActivity(intent1)
        }
    }

    fun checkUsername(name : String, id : Int) {
        val root = FirebaseDatabase.getInstance().getReference("people/$id/nickname")
        root.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                if (post.toString() == name)
                    Toast.makeText(getApplicationContext(),"This username already exists!", Toast.LENGTH_SHORT).show()
                else
                    if (post != null)
                        checkUsername(name, id + 1)
                    else {
                        val ref = FirebaseDatabase.getInstance().getReference("people/2")
                        ref.child("name").setValue("new user").addOnCompleteListener {}
                        ref.child("password").setValue("1234").addOnCompleteListener {}
                        ref.child("totalPoints").setValue("0").addOnCompleteListener {}
                    }
            }
        })
    }

}

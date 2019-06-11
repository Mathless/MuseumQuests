package com.example.MuseumQuests

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
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
            val name = username.text.toString().toLowerCase()
            val password = password_signup1.text.toString()

            when {
                password_signup1.text.toString() != password_signup2.text.toString() -> {
                    val toast: Toast = Toast.makeText(
                        getApplicationContext(),
                        "Passwords don't match",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
                name.length < 4 -> {
                    val toast: Toast = Toast.makeText(
                        getApplicationContext(),
                        "Username must have at least 4 characters!",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
                password.length < 4 -> {
                    val toast: Toast = Toast.makeText(
                        getApplicationContext(),
                        "Password must have at least 4 characters!",
                        Toast.LENGTH_SHORT
                    )
                    toast.show()
                }
                else -> {
                    checkUsername(name, 0, password, this)
                }
            }
        }
        button_signin_back.setOnClickListener{
            val intent1 = Intent(this, Start::class.java)
            startActivity(intent1)
        }
    }

    fun checkUsername(name : String, id : Int, password : String, ctx : Context) {
        val root = FirebaseDatabase.getInstance().getReference("people/$id/username")
        root.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("not implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                val post = p0.getValue(String::class.java)
                if (post.toString() == name)
                    Toast.makeText(getApplicationContext(),"This username already exists!", Toast.LENGTH_SHORT).show()
                else
                    if (post != null)
                        checkUsername(name, id + 1, password, ctx)
                    else {
                        val ref = FirebaseDatabase.getInstance().getReference("people/$id")
                        ref.child("username").setValue(name).addOnCompleteListener {}
                        ref.child("verification_code").setValue(password).addOnCompleteListener {}
                        ref.child("pointsEarned").setValue("0").addOnCompleteListener {}
                        val builder = AlertDialog.Builder(ctx)
                        builder.setTitle("New user added!")
                        builder.setMessage("Username: $name")
                        builder.setPositiveButton("Sign in"){_, _ ->
                            val intent = Intent(ctx, Start::class.java)
                            startActivity(intent)
                        }
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                    }
            }
        })
    }

}

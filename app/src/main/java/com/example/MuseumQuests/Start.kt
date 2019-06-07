package com.example.MuseumQuests

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_start.*

class Start : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels
        start_title.width = width / 2


        buttonStart.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }
}
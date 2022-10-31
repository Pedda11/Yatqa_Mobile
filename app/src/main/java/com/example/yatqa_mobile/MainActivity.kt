package com.example.yatqa_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)*/
    }
}
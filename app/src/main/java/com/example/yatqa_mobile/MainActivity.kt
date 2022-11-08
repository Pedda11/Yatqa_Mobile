package com.example.yatqa_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun bottomNavBarVisible(activeId :Int){
        val bottomNavBar: CardView = findViewById(R.id.cv_bottom_nav_bar)
        val home: LinearLayout = findViewById(R.id.ll_home)
        val stats: LinearLayout = findViewById(R.id.ll_stats)
        val server: LinearLayout = findViewById(R.id.ll_server)
        val other: LinearLayout = findViewById(R.id.ll_other)
        val advanced: LinearLayout = findViewById(R.id.ll_advanced)
        bottomNavBar.visibility = View.VISIBLE

        when(activeId){
            0 -> {
                home.setBackgroundColor(resources.getColor(R.color.light_blue))
                stats.setBackgroundColor(resources.getColor(R.color.white))
                server.setBackgroundColor(resources.getColor(R.color.white))
                other.setBackgroundColor(resources.getColor(R.color.white))
                advanced.setBackgroundColor(resources.getColor(R.color.white))
            }
            1 -> {
                home.setBackgroundColor(resources.getColor(R.color.white))
                stats.setBackgroundColor(resources.getColor(R.color.light_blue))
                server.setBackgroundColor(resources.getColor(R.color.white))
                other.setBackgroundColor(resources.getColor(R.color.white))
                advanced.setBackgroundColor(resources.getColor(R.color.white))
            }
            2 -> {
                home.setBackgroundColor(resources.getColor(R.color.white))
                stats.setBackgroundColor(resources.getColor(R.color.white))
                server.setBackgroundColor(resources.getColor(R.color.light_blue))
                other.setBackgroundColor(resources.getColor(R.color.white))
                advanced.setBackgroundColor(resources.getColor(R.color.white))
            }
            3 -> {
                home.setBackgroundColor(resources.getColor(R.color.white))
                stats.setBackgroundColor(resources.getColor(R.color.white))
                server.setBackgroundColor(resources.getColor(R.color.white))
                other.setBackgroundColor(resources.getColor(R.color.light_blue))
                advanced.setBackgroundColor(resources.getColor(R.color.white))
            }
            4 -> {
                home.setBackgroundColor(resources.getColor(R.color.white))
                stats.setBackgroundColor(resources.getColor(R.color.white))
                server.setBackgroundColor(resources.getColor(R.color.white))
                other.setBackgroundColor(resources.getColor(R.color.white))
                advanced.setBackgroundColor(resources.getColor(R.color.light_blue))
            }
        }
    }
}
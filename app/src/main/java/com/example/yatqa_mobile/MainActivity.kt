package com.example.yatqa_mobile

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.yatqa_mobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //make app fullscreen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        //apply navController
        (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).also {
            navController = it.navController
        }

        binding.llHome.setOnClickListener {
            bottomNavBarVisible(0)
            navController.navigate(R.id.favoritesFragment)
        }

        binding.llStats.setOnClickListener {
            bottomNavBarVisible(1)
            navController.navigate(R.id.globalServerFragment)
        }

        binding.llServer.setOnClickListener {
            bottomNavBarVisible(2)
            navController.navigate(R.id.serverListFragment)
        }
    }

    //bottom navigation bar visibility and highlighting
    fun bottomNavBarVisible(activeId: Int) {
        binding.cvBottomNavBar.visibility = View.VISIBLE

        when (activeId) {
            0 -> {
                binding.llHome.setBackgroundResource(R.drawable.shape)
                binding.llStats.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.llServer.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.llOther.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            }
            1 -> {
                binding.llHome.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.llStats.setBackgroundResource(R.drawable.shape)
                binding.llServer.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.llOther.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            }
            2 -> {
                binding.llHome.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.llStats.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.llServer.setBackgroundResource(R.drawable.shape)
                binding.llOther.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            }
            else -> {
                binding.llHome.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.llStats.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.llServer.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.llOther.setBackgroundResource(R.drawable.shape)
            }
        }
    }
}
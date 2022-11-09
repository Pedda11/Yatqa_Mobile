package com.example.yatqa_mobile

import android.content.res.Resources.Theme
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.yatqa_mobile.databinding.ActivityMainBinding
import com.example.yatqa_mobile.ui.global.GlobalServerFragment
import com.example.yatqa_mobile.ui.global.GlobalServerFragmentDirections

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

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

    fun bottomNavBarVisible(activeId: Int) {
        binding.cvBottomNavBar.visibility = View.VISIBLE

        when (activeId) {
            0 -> {
                binding.llHome.setBackgroundColor(ContextCompat.getColor(this, R.color.light_blue))
                binding.llStats.setBackgroundColor(resources.getColor(R.color.white))
                binding.llServer.setBackgroundColor(resources.getColor(R.color.white))
                binding.llOther.setBackgroundColor(resources.getColor(R.color.white))
                binding.llAdvanced.setBackgroundColor(resources.getColor(R.color.white))
            }
            1 -> {
                binding.llHome.setBackgroundColor(resources.getColor(R.color.white))
                binding.llStats.setBackgroundColor(resources.getColor(R.color.light_blue))
                binding.llServer.setBackgroundColor(resources.getColor(R.color.white))
                binding.llOther.setBackgroundColor(resources.getColor(R.color.white))
                binding.llAdvanced.setBackgroundColor(resources.getColor(R.color.white))
            }
            2 -> {
                binding.llHome.setBackgroundColor(resources.getColor(R.color.white))
                binding.llStats.setBackgroundColor(resources.getColor(R.color.white))
                binding.llServer.setBackgroundColor(resources.getColor(R.color.light_blue))
                binding.llOther.setBackgroundColor(resources.getColor(R.color.white))
                binding.llAdvanced.setBackgroundColor(resources.getColor(R.color.white))
            }
            3 -> {
                binding.llHome.setBackgroundColor(resources.getColor(R.color.white))
                binding.llStats.setBackgroundColor(resources.getColor(R.color.white))
                binding.llServer.setBackgroundColor(resources.getColor(R.color.white))
                binding.llOther.setBackgroundColor(resources.getColor(R.color.light_blue))
                binding.llAdvanced.setBackgroundColor(resources.getColor(R.color.white))
            }
            4 -> {
                binding.llHome.setBackgroundColor(resources.getColor(R.color.white))
                binding.llStats.setBackgroundColor(resources.getColor(R.color.white))
                binding.llServer.setBackgroundColor(resources.getColor(R.color.white))
                binding.llOther.setBackgroundColor(resources.getColor(R.color.white))
                binding.llAdvanced.setBackgroundColor(resources.getColor(R.color.light_blue))
            }
        }
    }
}
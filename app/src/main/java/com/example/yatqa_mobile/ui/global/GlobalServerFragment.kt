package com.example.yatqa_mobile.ui.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.adapter.GlobalStatsAdapter
import com.example.yatqa_mobile.databinding.FragmentGlobalserverStatsConfigBinding
import com.example.yatqa_mobile.ui.main.MainViewModel

class GlobalServerFragment: Fragment() {

    private lateinit var binding: FragmentGlobalserverStatsConfigBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_globalserver_stats_config,
            container,
            false
        )

        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = binding.rvStats
        recycler.adapter = GlobalStatsAdapter(listOf())
    }
}
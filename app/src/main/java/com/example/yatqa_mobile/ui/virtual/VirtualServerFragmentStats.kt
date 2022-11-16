package com.example.yatqa_mobile.ui.virtual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.databinding.FragmentVirtualserverStatsBinding
import com.example.yatqa_mobile.ui.main.MainViewModel

class VirtualServerFragmentStats : Fragment() {

    private lateinit var binding: FragmentVirtualserverStatsBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_virtualserver_stats,
            container,
            false
        )

        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var vsPort = requireArguments().getInt("vServerPort", 0)
        binding.tvVNameValue.text = viewModel.getVirtualServerInfo(vsPort).toString()
    }
}
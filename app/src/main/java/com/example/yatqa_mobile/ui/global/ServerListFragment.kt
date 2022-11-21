package com.example.yatqa_mobile.ui.global

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.adapter.ServerAdapter
import com.example.yatqa_mobile.databinding.FragmentServerlistBinding
import com.example.yatqa_mobile.ui.main.MainViewModel

class ServerListFragment : Fragment() {

    //DataBinding & ViewModel
    private lateinit var binding: FragmentServerlistBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_serverlist,
            container,
            false
        )

        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get VirtualServerList
        viewModel.getVirtualServerList()

        //generate adapter
        val recycler = binding.rvServerList

        viewModel.getGlobalDataCompleted.observe(
            viewLifecycleOwner){
            if (it){
                recycler.adapter = ServerAdapter(viewModel.vServerList)
            }
        }

        binding.rvServerList.setOnScrollChangeListener { v: View, scrollX: Int, scrollY: Int, _: Int, _: Int ->
            binding.swiperefresh.isEnabled = scrollY == 0
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.getVirtualServerList()
            recycler.adapter = ServerAdapter(viewModel.vServerList)
            binding.swiperefresh.isRefreshing = false
        }
    }
}
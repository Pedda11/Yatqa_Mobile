package com.example.yatqa_mobile.ui.virtual

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.databinding.FragmentVirtualserverMainBinding
import com.example.yatqa_mobile.ui.main.MainViewModel

class VirtualServerFragmentMain : Fragment() {

    private lateinit var binding: FragmentVirtualserverMainBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_virtualserver_main,
            container,
            false
        )

        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //loading virtual server infos
        val vServerPort = requireArguments().getInt("vServerPort", 0)
        viewModel.getCurrentVirtualServerDetails(vServerPort)
        viewModel.connectToVirtualServer()
        viewModel.getVirtualServerInfo()

        //load spinner
        val spinnerItemList = listOf(
            getString(R.string.vServerStats),
            getString(R.string.vServerAdvanced),
            getString(R.string.vServerBansAndCo),
            getString(R.string.vServerUserAndChannel),
            getString(R.string.vServerDataBase),
            getString(R.string.vServerRights),
            getString(R.string.vServerOthers)
        )

        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            spinnerItemList
        )

        arrayAdapter.setDropDownViewResource(R.layout.dropdown_item)
        binding.spinnerVirtualServerOptions.adapter = arrayAdapter

        //select spinner item
        binding.spinnerVirtualServerOptions.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(
                        requireContext(),
                        "selected = ${spinnerItemList[position]}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

    }
}
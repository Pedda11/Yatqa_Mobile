package com.example.yatqa_mobile.ui.virtual

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.databinding.FragmentVirtualserverStatsBinding
import com.example.yatqa_mobile.ui.main.MainViewModel
import com.github.theholywaffle.teamspeak3.api.VirtualServerProperty

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

        viewModel.vServerInfo.observe(
            viewLifecycleOwner
        ) {
            if (it != null) {

                var vServerName = it.name
                if (it.name.length > 25) {
                    vServerName = it.name.substring(0, 22) + "..."
                }
                binding.tvVNameValue.text = vServerName

                var vServerPhonetic = it.phoneticName
                if (it.phoneticName.length > 25) {
                    vServerPhonetic = it.phoneticName.substring(0, 22) + "..."
                }
                binding.tvVPhoneticValue.text = vServerPhonetic

                binding.tvVSlotsValue.text =
                    "${it.clientsOnline - it.queryClientsOnline}+${it.queryClientsOnline}/${it.maxClients}"

                binding.tvVMachineIdValue.text =
                    if (it.machineId.isNullOrEmpty()) "---" else it.machineId.toString()
                binding.tvVPortValue.text = it.port.toString()
                binding.tvVStateValue.text = it.status.name
                binding.tvVAutostartValue.text = it.isAutoStart.toString()

                binding.progressBarVserverUser.progress = it.clientsOnline
                binding.progressBarVserverUser.max = it.maxClients
            }
        }

        binding.tvVName.setOnClickListener {
            var propertyKey: VirtualServerProperty = VirtualServerProperty.VIRTUALSERVER_NAME
            showDialog(propertyKey, viewModel.vServerInfo.value!!.name)
        }

        binding.tvVPhonetic.setOnClickListener {
            var propertyKey: VirtualServerProperty =
                VirtualServerProperty.VIRTUALSERVER_NAME_PHONETIC
            showDialog(propertyKey, viewModel.vServerInfo.value!!.phoneticName)
        }

        binding.tvVPort.setOnClickListener {
            var propertyKey: VirtualServerProperty = VirtualServerProperty.VIRTUALSERVER_PORT
            showDialog(propertyKey, viewModel.vServerInfo.value!!.port.toString())
        }

        binding.tvVAutostart.setOnClickListener {
            var propertyKey: VirtualServerProperty = VirtualServerProperty.VIRTUALSERVER_AUTOSTART
            showDialog(propertyKey, viewModel.vServerInfo.value!!.isAutoStart.toString())
        }
    }

    //AlertDialog with editText
    private fun showDialog(
        propKey: VirtualServerProperty,
        propertyCurrentValue: String
    ) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_textbox, null)
        val textBox: EditText = dialogLayout.findViewById(R.id.edit_text_new_value)

        textBox.setText(propertyCurrentValue)

        with(builder) {
            setTitle(propKey.toString())
            setPositiveButton("OK") { _, _ ->
                var newValue = textBox.text.toString()

                if (newValue.isEmpty() && propKey != VirtualServerProperty.VIRTUALSERVER_NAME_PHONETIC) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.AlertNotEmpty),
                        Toast.LENGTH_SHORT
                    ).show()
                    showDialog(propKey, propertyCurrentValue)
                    return@setPositiveButton
                }

                when (propKey) {
                    VirtualServerProperty.VIRTUALSERVER_MAXCLIENTS -> {
                        try {
                            val parseToInt: Int = newValue.toInt()
                        } catch (e: Exception) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.AlertNotOnlyNumeric),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@setPositiveButton
                        }
                    }
                    VirtualServerProperty.VIRTUALSERVER_PORT -> {
                        try {
                            val parseToInt: Int = newValue.toInt()
                        } catch (e: Exception) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.AlertNotOnlyNumeric),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@setPositiveButton
                        }
                    }
                    VirtualServerProperty.VIRTUALSERVER_AUTOSTART -> {

                        if (!(newValue == "true" || newValue == "false")) {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.AlertNotOnlyBool),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@setPositiveButton
                        }else{
                            if (newValue == "true"){
                                newValue = "1"
                            }else{
                                newValue = "0"
                            }
                        }
                    }
                    else -> {
                    }
                }

                val property: MutableMap<VirtualServerProperty, String> = mutableMapOf(
                    propKey to newValue
                )

                viewModel.setVirtualServerProperty(property)

                viewModel.getVirtualServerInfo()
            }
            setView(dialogLayout)
            show()
        }
    }
}
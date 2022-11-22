package com.example.yatqa_mobile.ui.global

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.yatqa_mobile.MainActivity
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.databinding.FragmentGlobalserverStatsConfigBinding
import com.example.yatqa_mobile.ui.main.MainViewModel
import com.github.theholywaffle.teamspeak3.api.ServerInstanceProperty

class GlobalServerFragment : Fragment() {

    private lateinit var binding: FragmentGlobalserverStatsConfigBinding
    private val viewModel: MainViewModel by activityViewModels()

    private val giB = 1073741824
    private val miB = 1048576
    private val mB = 1000000
    private val kiB = 1024

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).bottomNavBarVisible(1)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scrollView.setOnScrollChangeListener { v: View, scrollX: Int, scrollY: Int, _: Int, _: Int ->
            binding.swiperefresh.isEnabled = scrollY == 0
        }

        binding.swiperefresh.setOnRefreshListener {
            viewModel.getGlobalInfo()
            binding.swiperefresh.isRefreshing = false
        }

        //observe telnet connection
        viewModel.connectionCompleted.observe(
            viewLifecycleOwner
        ) {
            if (it) {
                viewModel.getGlobalInfo()
                viewModel.unsetConnectComplete()
            }
        }

        // " get HostInfo and InstanceInfo
        viewModel.getGlobalDataCompleted.observe(
            viewLifecycleOwner
        ) {
            if (it) {

                //Virtual Server
                binding.tvServerValue.text =
                    viewModel.hostInfo.totalRunningServers.toString()
                binding.tvChannelValue.text = viewModel.hostInfo.totalChannels.toString()
                binding.tvUserValue.text = String.format(
                    resources.getString(R.string.userOnlineValue),
                    viewModel.hostInfo.totalClientsOnline.toString(),
                    viewModel.hostInfo.totalMaxClients.toString()
                )

                binding.ivVServerLeftIcon.setImageResource(R.drawable.server01_1)

                /**
                 * change from clients to total and running servers
                 */
                binding.progressBar.progress = viewModel.hostInfo.totalClientsOnline
                binding.progressBar.max = viewModel.hostInfo.totalMaxClients


                //Current upload
                binding.tvCurUploadTotalSekValue.text = getString(
                    R.string.strKibs, String.format(
                        "%.2f",
                        viewModel.hostInfo.bandwidthSentLastSecond.toDouble() / kiB
                    )
                )
                binding.tvCurUploadTotalMinValue.text = getString(
                    R.string.strKibs, String.format(
                        "%.2f", viewModel.hostInfo.bandwidthSentLastMinute.toDouble() / kiB
                    )
                )
                binding.tvCurUploadDataSekValue.text = getString(
                    R.string.strKibs, String.format(
                        "%.2f",
                        viewModel.hostInfo.fileTransferBandwidthSent.toDouble() / kiB
                    )
                )
                binding.ivCurrUploadLeftIcon.setImageResource(R.drawable.upload)

                //Current download
                binding.tvCurDownloadTotalSekValue.text = getString(
                    R.string.strKibs, String.format(
                        "%.2f",
                        viewModel.hostInfo.bandwidthReceivedLastSecond.toDouble() / kiB
                    )
                )
                binding.tvCurDownloadTotalMinValue.text = getString(
                    R.string.strKibs, String.format(
                        "%.2f",
                        viewModel.hostInfo.bandwidthReceivedLastMinute.toDouble() / kiB
                    )
                )

                binding.tvCurDownloadDataSekValue.text = getString(
                    R.string.strKibs, String.format(
                        "%.2f",
                        viewModel.hostInfo.fileTransferBandwidthReceived.toDouble() / kiB
                    )
                )
                binding.ivCurrDownloadLeftIcon.setImageResource(R.drawable.download)

                //Transferred data
                binding.tvTransferedSentValue.text = getString(
                    R.string.strGib, String.format(
                        "%.2f",
                        viewModel.hostInfo.bytesSentTotal.toDouble() / giB
                    )
                )
                binding.tvTransferedReceivedValue.text = getString(
                    R.string.strGib, String.format(
                        "%.2f",
                        viewModel.hostInfo.bytesReceivedTotal.toDouble() / giB
                    )
                )
                binding.tvTransferedSumValue.text = getString(
                    R.string.strGib, String.format(
                        "%.2f", (viewModel.hostInfo.bytesSentTotal.toDouble() +
                                viewModel.hostInfo.bytesReceivedTotal.toDouble()) / giB
                    )
                )
                binding.ivTransferedDataLeft.setImageResource(R.drawable.tranferred_data)
                binding.ivTransferedDataRight.setImageResource(R.drawable.diagram2)

                //Transferred packets
                binding.tvPacketsSentValue.text = getString(
                    R.string.strMillion, String.format(
                        "%.2f",
                        viewModel.hostInfo.packetsSentTotal.toDouble() / mB
                    )
                )
                binding.tvPacketsReceivedValue.text = getString(
                    R.string.strMillion, String.format(
                        "%.2f",
                        viewModel.hostInfo.packetsReceivedTotal.toDouble() / mB
                    )
                )
                binding.tvPacketsSumValue.text = getString(
                    R.string.strMillion, String.format(
                        "%.2f", (viewModel.hostInfo.packetsSentTotal.toDouble() +
                                viewModel.hostInfo.packetsReceivedTotal.toDouble()) / mB
                    )
                )

                binding.ivPacketsLeft.setImageResource(R.drawable.transferred_packages)
                binding.ivPacketsRight.setImageResource(R.drawable.diagram2)

                //Transferred files
                binding.tvFilesSentValue.text = getString(
                    R.string.strMib, String.format(
                        "%.2f",
                        viewModel.hostInfo.fileTransferBytesSent.toDouble() / miB
                    )
                )
                binding.tvFilesReceivedValue.text = getString(
                    R.string.strMib, String.format(
                        "%.2f",
                        viewModel.hostInfo.fileTransferBytesReceived.toDouble() / kiB
                    )
                )
                binding.tvFilesSumValue.text = getString(
                    R.string.strMib, String.format(
                        "%.2f", (viewModel.hostInfo.fileTransferBytesSent.toDouble() +
                                viewModel.hostInfo.fileTransferBytesReceived.toDouble()) / miB
                    )
                )

                binding.ivFilesLeft.setImageResource(R.drawable.transferred_files)
                binding.ivFilesRight.setImageResource(R.drawable.diagram2)

                //Server queries
                binding.tvGuestQueryGroupValue.text =
                    viewModel.instanceInfo.guestServerQueryGroup.toString()

                binding.tvCommandsTillFloodValue.text =
                    viewModel.bigIntFormat.format(viewModel.instanceInfo.maxFloodCommands)

                binding.tvPeriodFloodValue.text = viewModel.instanceInfo.maxFloodTime.toString()
                binding.tvBanTimeFloodValue.text =
                    viewModel.instanceInfo.floodBanTime.toString()
                binding.tvQueryConnectionsPerIpValue.text =
                    viewModel.instanceInfo["serverinstance_serverquery_max_connections_per_ip"]

                binding.ivServerQuerys.setImageResource(R.drawable.spyglass)

                //Standard groups
                binding.tvServeradminGroupTemplateId.text =
                    viewModel.instanceInfo.serverAdminGroup.toString()
                binding.tvServerguestGroupTemplateId.text =
                    viewModel.instanceInfo.defaultServerGroup.toString()
                binding.tvChanneladminGroupTemplateId.text =
                    viewModel.instanceInfo.channelAdminGroup.toString()
                binding.tvChannelguestGroupTemplateId.text =
                    viewModel.instanceInfo.defaultChannelGroup.toString()

                binding.ivStandradGroups.setImageResource(R.drawable.groups)

                //file transfer user view
                val currentBandwidthUpload =
                    viewModel.instanceInfo["serverinstance_max_upload_total_bandwidth"].toBigInteger()
                val currentBandwidthDownload =
                    viewModel.instanceInfo["serverinstance_max_download_total_bandwidth"].toBigInteger()
                val defaultBandwidth = ("18446744073709551615").toBigInteger()

                if (currentBandwidthUpload == defaultBandwidth) {
                    binding.tvTransferMaxUploadValue.text = getString(R.string.unlimited)
                } else if (currentBandwidthUpload > (giB).toBigInteger()) {
                    binding.tvTransferMaxUploadValue.text = getString(
                        R.string.strGibs, String.format(
                            "%.2f",
                            (currentBandwidthUpload.toDouble() / giB)
                        )
                    )
                } else if (currentBandwidthUpload > (miB).toBigInteger()) {
                    binding.tvTransferMaxUploadValue.text = getString(
                        R.string.strMibs, String.format(
                            "%.2f",
                            (currentBandwidthUpload.toDouble() / miB)
                        )
                    )
                } else {
                    binding.tvTransferMaxUploadValue.text = getString(
                        R.string.strKibs, String.format(
                            "%.2f",
                            (currentBandwidthUpload.toDouble() / kiB)
                        )
                    )
                }

                if (currentBandwidthDownload == defaultBandwidth) {
                    binding.tvTransferMaxDownloadValue.text = getString(R.string.unlimited)
                } else if (currentBandwidthDownload > (giB).toBigInteger()) {
                    binding.tvTransferMaxDownloadValue.text = getString(
                        R.string.strGibs, String.format(
                            "%.2f",
                            (currentBandwidthDownload.toDouble() / giB)
                        )
                    )
                } else if (currentBandwidthDownload > (miB).toBigInteger()) {
                    binding.tvTransferMaxDownloadValue.text = getString(
                        R.string.strMibs, String.format(
                            "%.2f",
                            (currentBandwidthDownload.toDouble() / miB)
                        )
                    )
                } else {
                    binding.tvTransferMaxDownloadValue.text = getString(
                        R.string.strKibs, String.format(
                            "%.2f",
                            (currentBandwidthDownload.toDouble() / kiB)
                        )
                    )
                }

                binding.tvTransferPortValue.text =
                    viewModel.instanceInfo.fileTransferPort.toString()

                binding.ivTransfer.setImageResource(R.drawable.transfer)

                viewModel.unsetGetDataComplete()
            }
        }

        //config setter
        binding.tvGuestQueryGroup.setOnClickListener {
            val property = ServerInstanceProperty.SERVERINSTANCE_GUEST_SERVERQUERY_GROUP
            showDialog(
                property,
                binding.tvGuestQueryGroupValue.text.toString()
            )
        }

        binding.tvCommandsTillFlood.setOnClickListener {
            val property = ServerInstanceProperty.SERVERINSTANCE_SERVERQUERY_FLOOD_COMMANDS

            val value = binding.tvCommandsTillFloodValue.text.toString().replace(".", "")
            showDialog(
                property,
                value
            )
        }

        binding.tvPeriodFlood.setOnClickListener {
            val property = ServerInstanceProperty.SERVERINSTANCE_SERVERQUERY_FLOOD_TIME
            showDialog(
                property,
                binding.tvPeriodFloodValue.text.toString()
            )
        }

        binding.tvBanTimeFlood.setOnClickListener {
            val property = ServerInstanceProperty.SERVERINSTANCE_SERVERQUERY_BAN_TIME
            showDialog(
                property,
                binding.tvBanTimeFloodValue.text.toString()
            )
        }

        binding.tvServeradminGroupTemplate.setOnClickListener {
            val property = ServerInstanceProperty.SERVERINSTANCE_TEMPLATE_SERVERADMIN_GROUP
            showDialog(
                property,
                binding.tvServeradminGroupTemplateId.text.toString()
            )
        }

        binding.tvServerguestGroupTemplate.setOnClickListener {
            val property = ServerInstanceProperty.SERVERINSTANCE_TEMPLATE_SERVERDEFAULT_GROUP
            showDialog(
                property,
                binding.tvServerguestGroupTemplateId.text.toString()
            )
        }

        binding.tvChanneladminGroupTemplate.setOnClickListener {
            val property = ServerInstanceProperty.SERVERINSTANCE_TEMPLATE_CHANNELADMIN_GROUP
            showDialog(
                property,
                binding.tvChanneladminGroupTemplateId.text.toString()
            )
        }

        binding.tvChannelguestGroupTemplate.setOnClickListener {
            val property = ServerInstanceProperty.SERVERINSTANCE_TEMPLATE_CHANNELDEFAULT_GROUP
            showDialog(
                property,
                binding.tvChannelguestGroupTemplateId.text.toString()
            )
        }

        binding.tvTransferMaxUpload.setOnClickListener {
            val property = ServerInstanceProperty.SERVERINSTANCE_MAX_UPLOAD_TOTAL_BANDWIDTH
            showDialog(
                property,
                viewModel.instanceInfo["serverinstance_max_upload_total_bandwidth"]
            )
        }

        binding.tvTransferMaxDownload.setOnClickListener {
            val property = ServerInstanceProperty.SERVERINSTANCE_MAX_DOWNLOAD_TOTAL_BANDWIDTH
            showDialog(
                property,
                viewModel.instanceInfo["serverinstance_max_download_total_bandwidth"]
            )
        }

        binding.tvTransferPort.setOnClickListener {
            val property = ServerInstanceProperty.SERVERINSTANCE_FILETRANSFER_PORT
            showDialog(
                property,
                binding.tvTransferPortValue.text.toString()
            )
        }
    }

    //AlertDialog with editText
    private fun showDialog(
        prop: ServerInstanceProperty,
        propertyCurrentValue: String
    ) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_textbox, null)
        val textBox: EditText = dialogLayout.findViewById(R.id.edit_text_new_value)

        textBox.setText(propertyCurrentValue)

        with(builder) {
            setTitle(prop.toString())
            setPositiveButton("OK") { _, _ ->

                val newValue = textBox.text.toString()

                if (newValue.isEmpty()){
                    Toast.makeText(requireContext(),getString(R.string.AlertNotEmpty),Toast.LENGTH_SHORT).show()
                    showDialog(prop, propertyCurrentValue)
                    return@setPositiveButton
                }

                viewModel.setServerInstanceProperty(prop, newValue)

                viewModel.getGlobalInfo()
            }
            setView(dialogLayout)
            show()
        }
    }
}
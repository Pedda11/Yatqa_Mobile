package com.example.yatqa_mobile.ui.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.databinding.FragmentGlobalserverStatsConfigBinding
import com.example.yatqa_mobile.ui.main.MainViewModel

class GlobalServerFragment : Fragment() {

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

        viewModel.connectionCompleted.observe(
            viewLifecycleOwner,
            Observer {
                if (it) {
                    viewModel.getHostInfo()
                    viewModel.unsetConnectComplete()
                }
            }
        )

        viewModel.getGlobalDataCompleted.observe(
            viewLifecycleOwner,
            Observer {
                if (it) {

                    //Virtual Server
                    binding.tvServerValue.text =
                        viewModel.hostInfo.totalRunningServers.toString() + "/10"
                    binding.tvChannelValue.text = viewModel.hostInfo.totalChannels.toString()
                    binding.tvUserValue.text =
                        viewModel.hostInfo.totalClientsOnline.toString() + "/" + viewModel.hostInfo.totalMaxClients.toString()

                    binding.ivVServerLeftIcon.setImageResource(R.drawable.server01_1)
                    binding.ivVServerRightIcon.setImageResource(R.drawable.diagram1)

                    //Current upload
                    binding.tvCurUploadTotalSekValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.bandwidthSentLastSecond.toDouble() / 1024
                        ) + " KiB/s"
                    binding.tvCurUploadTotalMinValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.bandwidthSentLastMinute.toDouble() / 1024
                        ) + " KiB/s"
                    binding.tvCurUploadDataSekValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.fileTransferBandwidthSent.toDouble() / 1024
                        ) + " KiB/s"

                    binding.ivCurrUploadLeftIcon.setImageResource(R.drawable.upload)

                    //Current download
                    binding.tvCurDownloadTotalSekValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.bandwidthReceivedLastSecond.toDouble() / 1024
                        ) + " KiB/s"
                    binding.tvCurDownloadTotalMinValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.bandwidthReceivedLastMinute.toDouble() / 1024
                        ) + " KiB/s"
                    binding.tvCurDownloadDataSekValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.fileTransferBandwidthReceived.toDouble() / 1024
                        ) + " KiB/s"

                    binding.ivCurrDownloadLeftIcon.setImageResource(R.drawable.download)

                    //Transferred data
                    binding.tvTransferedSendValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.bytesSentTotal.toDouble() / 1073741824
                        ) + " GiB"
                    binding.tvTransferedReceivedValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.bytesReceivedTotal.toDouble() / 1073741824
                        ) + " GiB"
                    binding.tvTransferedSumValue.text =
                        String.format(
                            "%.2f", (viewModel.hostInfo.bytesSentTotal.toDouble() +
                                    viewModel.hostInfo.bytesReceivedTotal.toDouble()) / 1073741824
                        ) + " GiB"

                    binding.ivTransferedDataLeft.setImageResource(R.drawable.tranferred_data)
                    binding.ivTransferedDataRight.setImageResource(R.drawable.diagram2)

                    //Transferred packets
                    binding.tvPacketsSendValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.packetsSentTotal.toDouble() / 1000000000
                        ) + " G"
                    binding.tvPacketsReceivedValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.packetsReceivedTotal.toDouble() / 1000000
                        ) + " M"
                    binding.tvPacketsSumValue.text =
                        String.format(
                            "%.2f", (viewModel.hostInfo.packetsSentTotal.toDouble() +
                                    viewModel.hostInfo.packetsReceivedTotal.toDouble()) / 1000000000
                        ) + " G"

                    binding.ivPacketsLeft.setImageResource(R.drawable.transferred_packages)
                    binding.ivPacketsRight.setImageResource(R.drawable.diagram2)

                    //Transferred files
                    binding.tvFilesSendValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.fileTransferBytesSent.toDouble() / 1048576
                        ) + " MiB"
                    binding.tvFilesReceivedValue.text =
                        String.format(
                            "%.2f",
                            viewModel.hostInfo.fileTransferBytesReceived.toDouble() / 1048576
                        ) + " MiB"
                    binding.tvFilesSumValue.text =
                        String.format(
                            "%.2f", (viewModel.hostInfo.fileTransferBytesSent.toDouble() +
                                    viewModel.hostInfo.fileTransferBytesReceived.toDouble()) / 1048576
                        ) + " MiB"

                    binding.ivFilesLeft.setImageResource(R.drawable.transferred_files)
                    binding.ivFilesRight.setImageResource(R.drawable.diagram2)

                    viewModel.unsetGetDataComplete()
                }
            }
        )
    }
}
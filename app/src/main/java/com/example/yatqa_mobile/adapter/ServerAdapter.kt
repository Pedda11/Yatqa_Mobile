package com.example.yatqa_mobile.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.data.TAG
import com.github.theholywaffle.teamspeak3.api.wrapper.VirtualServer
import java.util.concurrent.TimeUnit


class ServerAdapter(
    private val dataset: MutableList<VirtualServer>
) : RecyclerView.Adapter<ServerAdapter.ItemViewHolder>() {

    /**
     * for the onAttachedToRecyclerView fun
     */
    lateinit var serverRecycler: RecyclerView

    /**
     * All needed variables
     */
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.cv_server_item)
        val vServerName: TextView = itemView.findViewById(R.id.tv_serverName)
        val vServerPort: TextView = itemView.findViewById(R.id.tv_port)
        val vServerSlots: TextView = itemView.findViewById(R.id.tv_slots)
        val vServerOnlineSince: TextView = itemView.findViewById(R.id.tv_running_since)
        val vServerAutoStart: TextView = itemView.findViewById(R.id.tv_autostart)
        val vServerId: TextView = itemView.findViewById(R.id.tv_serverId)
        val btnStart: Button = itemView.findViewById(R.id.btn_server_start)
        val btnStop: Button = itemView.findViewById(R.id.btn_server_stop)
        val btnDel: Button = itemView.findViewById(R.id.btn_server_del)
    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.server_list_item, parent, false)

        return ItemViewHolder(itemLayout)
    }

    /**
     * hier findet der Recyclingprozess statt
     * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // Hole den Favoriten aus dem dataset
        val vServer = dataset[position]

        holder.vServerName.text = vServer.name
        holder.vServerPort.text = vServer.port.toString()
        holder.vServerSlots.text =
            "${vServer.clientsOnline - vServer.queryClientsOnline}+${vServer.queryClientsOnline}/${vServer.maxClients}"

        val seconds = vServer.uptime
        val days: Long = TimeUnit.SECONDS.toDays(seconds);
        val hours: Long =
            TimeUnit.SECONDS.toHours(seconds) - TimeUnit.SECONDS.toDays(seconds) * 24
        val minute: Long =
            TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60

        holder.vServerOnlineSince.text = "${days}d ${hours}h ${minute}m"
        holder.vServerAutoStart.text = vServer.isAutoStart.toString()
        holder.vServerId.text = vServer.id.toString()


        /**
         * change visibility from the edit and delete button in all viewholder
         */
        holder.card.setOnLongClickListener {

            for (c in dataset.indices){
                serverRecycler.findViewHolderForAdapterPosition(c).also { holder ->

                    try {
                        (holder as ItemViewHolder).btnStart.visibility = View.GONE
                        holder.btnStop.visibility = View.GONE
                        holder.btnDel.visibility = View.GONE
                    }catch (e:Exception){
                        Log.e(TAG, "Hide buttons in recyclerview")
                    }
                }
            }

            /**
             * change visibility from the edit and delete button in current viewholder
             */
            holder.btnStart.visibility = View.VISIBLE
            holder.btnStop.visibility = View.VISIBLE
            holder.btnDel.visibility = View.VISIBLE
            true
        }
    }

    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        serverRecycler = recyclerView
    }
}

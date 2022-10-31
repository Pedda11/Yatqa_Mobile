package com.example.yatqa_mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.data.datamodels.Login
import com.github.theholywaffle.teamspeak3.TS3Config
import com.github.theholywaffle.teamspeak3.TS3Query

class GlobalStatsAdapter(
    private val dataset: List<Login>
) : RecyclerView.Adapter<GlobalStatsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.cv_fav_item)
        val tvListName: TextView = itemView.findViewById(R.id.tv_listname)
        val tvIp: TextView = itemView.findViewById(R.id.tv_ip)
        val tvQPort: TextView = itemView.findViewById(R.id.tv_qport)
        val tvPort: TextView = itemView.findViewById(R.id.tv_port)
        val tvUserName: TextView = itemView.findViewById(R.id.tv_username)
        val btnEdit: Button = itemView.findViewById(R.id.btn_edit)
    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.fav_item, parent, false)

        return ItemViewHolder(itemLayout)
    }

    /**
     * hier findet der Recyclingprozess statt
     * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {



    }

    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return dataset.size
    }
}

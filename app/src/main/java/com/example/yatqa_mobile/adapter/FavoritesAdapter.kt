package com.example.yatqa_mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.data.datamodels.Login
import com.example.yatqa_mobile.ui.login.FavoritesFragmentDirections

class FavoritesAdapter(
    private val dataset: List<Login>,
    val ts3ApiConnect: (Login) -> Unit,
    val removeLogin: (Login) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.ItemViewHolder>() {

    /**
     * for the onAttachedToRecyclerView fun
     */
    lateinit var fovoritesRecycler:RecyclerView

    /**
     * All needed variables
     */
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.cv_fav_item)
        val tvListName: TextView = itemView.findViewById(R.id.tv_fav_name)
        val tvIp: TextView = itemView.findViewById(R.id.tv_ip)
        val tvQPort: TextView = itemView.findViewById(R.id.tv_qPort)
        val tvPort: TextView = itemView.findViewById(R.id.tv_fav_port)
        val tvUserName: TextView = itemView.findViewById(R.id.tv_user_name)
        val btnEdit: Button = itemView.findViewById(R.id.btn_edit)
        val btnDel: Button = itemView.findViewById(R.id.btn_del)
    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.fav_list_item, parent, false)

        return ItemViewHolder(itemLayout)
    }

    /**
     * hier findet der Recyclingprozess statt
     * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        // Hole den Favoriten aus dem dataset
        val fav = dataset[position]

        // Fill the Textviews
        holder.tvListName.text = fav.listName
        holder.tvIp.text = fav.ip
        holder.tvQPort.text = fav.qPort.toString()
        holder.tvPort.text = fav.port.toString()
        holder.tvUserName.text = fav.userName

        /**
         * change visibility from the edit and delete button in all viewholder
         */
        holder.card.setOnLongClickListener {

            for (c in dataset.indices){
                fovoritesRecycler.findViewHolderForAdapterPosition(c).also { holder ->

                    (holder as ItemViewHolder).btnEdit.visibility = View.GONE
                    holder.btnDel.visibility = View.GONE
                }
            }

            /**
             * change visibility from the edit and delete button in current viewholder
             */
            holder.btnEdit.visibility = View.VISIBLE
            holder.btnDel.visibility = View.VISIBLE
            true
        }

        /**
         * delete button
         */
        holder.btnDel.setOnClickListener {
            removeLogin(fav)
            notifyDataSetChanged()
        }

        /**
         * edit button
         */
        holder.btnEdit.setOnClickListener {
            holder.itemView.findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToLoginFragment(fav.id))
        }

        /**
         * connect to server
         */
        holder.card.setOnClickListener {
            ts3ApiConnect(fav)
            holder.itemView.findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToGlobalServerFragment())
        }
    }

    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return dataset.size
    }

    /**
     *get the recyclerview to change visibilitys in other viewholders
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        fovoritesRecycler = recyclerView
    }
}

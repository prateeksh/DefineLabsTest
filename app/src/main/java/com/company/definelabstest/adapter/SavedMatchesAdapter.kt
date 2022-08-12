package com.company.definelabstest.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.company.definelabstest.R
import com.company.definelabstest.model.Matches


class SavedMatchesAdapter (private val matches: MutableList<Matches.Response.Venues>, private val context: Context): RecyclerView.Adapter<SavedMatchesAdapter.SavedMatchesViewHolder>(){

    var onItemClick: ((Matches.Response.Venues) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMatchesViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.matches_view_layout, parent, false)
        Log.e("CoinsAdapter ", "adapter called: " )
        return SavedMatchesViewHolder(view)

    }

    override fun onBindViewHolder(holder: SavedMatchesViewHolder, position: Int) {

        holder.matchId.text = matches.get(position).id
        holder.matchName.text = matches.get(position).name
        if (matches.get(position).verified == true){
            holder.fav.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_24, null))
        }
    }

    override fun getItemCount(): Int {

        return matches.size
    }




    inner class SavedMatchesViewHolder (ItemView: View) : RecyclerView.ViewHolder(ItemView){

        val matchId: TextView = itemView.findViewById(R.id.id)
        val matchName: TextView = itemView.findViewById(R.id.name)
        val fav: ImageView = itemView.findViewById(R.id.favorite_icon)

        init {
            fav.setOnClickListener {
                onItemClick?.invoke(matches[adapterPosition])
                matches.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
                notifyItemRangeChanged(adapterPosition,matches.size)
                fav.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_border_24, null))
            }
        }
    }

}
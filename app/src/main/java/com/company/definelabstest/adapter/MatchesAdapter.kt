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
import com.company.definelabstest.room.MatchesDatabase
import kotlinx.coroutines.coroutineScope

class MatchesAdapter (private val matches: Matches, private val context: Context): RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder>(){

    var onItemClick: ((Matches.Response.Venues) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.matches_view_layout, parent, false)

        return MatchesViewHolder(view)

    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {

        holder.matchId.text = matches.response!!.venues.get(position).id
        holder.matchName.text = matches.response!!.venues.get(position).name

        holder.fav.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_border_24, null))
    }

    override fun getItemCount(): Int {

       return matches.response!!.venues.size
    }

    inner class MatchesViewHolder (ItemView: View) : RecyclerView.ViewHolder(ItemView){

        val matchId: TextView = itemView.findViewById(R.id.id)
        val matchName: TextView = itemView.findViewById(R.id.name)
        val fav: ImageView = itemView.findViewById(R.id.favorite_icon)

        init {
            fav.setOnClickListener {
                onItemClick?.invoke(matches.response!!.venues[adapterPosition])

                fav.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_24, null))
            }
        }
    }

}
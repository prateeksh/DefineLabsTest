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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MatchesAdapter (private val matches: Matches, private val context: Context): RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder>(){

    var onItemClick: ((Matches.Response.Venues) -> Unit)? = null
    private lateinit var database: MatchesDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.matches_view_layout, parent, false)
        database = MatchesDatabase.getDatabase(context)

        return MatchesViewHolder(view)

    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {

        val id = matches.response!!.venues.get(position).id
        holder.matchId.text = id
        holder.matchName.text = matches.response!!.venues.get(position).name

        holder.fav.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_border_24, null))

        CoroutineScope(Dispatchers.IO).launch {
            val count = database.userDao().count(id)
            if ( count == 1) {
                holder.fav.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_24, null))
            }
        }
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
                var count: Int
                CoroutineScope(Dispatchers.IO).launch {
                     count =
                        database.userDao().count(matches.response!!.venues[adapterPosition].id)
                    if ( count == 1) {
                        fav.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_border_24, null))
                    }
                }
                fav.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.outline_star_24, null))

            }
        }
    }

}
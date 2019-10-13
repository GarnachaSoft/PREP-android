package org.prepciudadano.prepciudadano.adapters

import android.app.ProgressDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.prepciudadano.prepciudadano.R
import org.prepciudadano.prepciudadano.classes.Card

class CardAdapter(var list:ArrayList<Card>): androidx.recyclerview.widget.RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(view:View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view){

        fun bindItems(data:Card){
            val title:TextView = itemView.findViewById(R.id.card_title)
            val description:TextView = itemView.findViewById(R.id.card_description)
            val thumbnail:ImageView = itemView.findViewById(R.id.card_thumbnail)

            title.text = data.name
            description.text = data.description
            thumbnail.setImageResource(data.thumbnail)

            itemView.setOnClickListener {

                itemView.context.startActivity(data.intent)
            }
        }
    }
}
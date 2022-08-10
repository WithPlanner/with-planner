package com.shop.withplanner.recyler_view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shop.withplanner.R

class ContentsAdapter (val context : Context, val List : MutableList<ContentsModel>) : RecyclerView.Adapter<ContentsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)

        return ViewHolder(v)
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (itemClick != null) {
            holder?.itemView.setOnClickListener { v ->
                itemClick!!.onClick(v, position)
            }
        }
        holder.bindItems(List[position])
    }

    override fun getItemCount(): Int {
        return List.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: ContentsModel) {
            val rvImg = itemView.findViewById<ImageView>(R.id.rvImageArea)
            rvImg.clipToOutline = true
            val rvText = itemView.findViewById<TextView>(R.id.rvTextArea)

            rvText.text = item.titleText

            Glide.with(context)
                .load(item.imageUrl)
                .into(rvImg)

        }
    }
}

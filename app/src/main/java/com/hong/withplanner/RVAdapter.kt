package com.hong.withplanner

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RVAdapter (val context : Context, val List : MutableList<ContentsModel>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)

        return ViewHolder(v)
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null


    override fun onBindViewHolder(holder: RVAdapter.ViewHolder, position: Int) {

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
            Log.d("TAG", item.imageUrl)

            Glide.with(context)
                .load(item.imageUrl)
                .into(rvImg)

        }
    }
}

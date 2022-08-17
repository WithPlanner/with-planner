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

class SearchAdapter(val context : Context, val searchList: MutableList<SearchModel>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_searched, parent, false)
        return ViewHolder(v)
    }

    var itemClick: ContentsAdapter.ItemClick? = null

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        if (itemClick != null) {
            holder?.itemView?.setOnClickListener { v ->
                itemClick!!.onClick(v, position)
            }
        }
        holder.bindItems(searchList[position])
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.communityName)
        val category: TextView = itemView.findViewById(R.id.category)
        val type: TextView = itemView.findViewById(R.id.communityType)
        val img: ImageView = itemView.findViewById(R.id.communityImg)
        val postIcon: ImageView = itemView.findViewById(R.id.postIcon)
        val mapIcon: ImageView = itemView.findViewById(R.id.mapIcon)
        val lockIcon: ImageView = itemView.findViewById(R.id.lockImg)


        fun bindItems(item: SearchModel) {
            name.text = item.community_name
            category.text = item.community_category
            type.text = item.community_type

            if(item.community_img!=null) {
                Glide.with(itemView).load(item.community_img).into(img)
            }

            if(item.community_type == "게시글 인증") {
                postIcon.visibility = View.VISIBLE
                mapIcon.visibility = View.INVISIBLE
            }
            else if(item.community_type == "위치 인증") {
                postIcon.visibility = View.INVISIBLE
                mapIcon.visibility = View.VISIBLE
            }
            if(item.community_publicType == "publicType") {
                lockIcon.visibility = View.INVISIBLE
            }
            else if(item.community_publicType == "privateType") {
                lockIcon.visibility = View.VISIBLE
            }
        }
    }

}
package com.shop.withplanner.recyler_view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shop.withplanner.R
import org.w3c.dom.Text

class UserAdapter(val context : Context, val userList: MutableList<UserModel>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_user, parent, false)
        return ViewHolder(v)
    }

    var itemClick: ContentsAdapter.ItemClick? = null

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nickname: TextView = itemView.findViewById(R.id.nickname)
        val iconImg: ImageView = itemView.findViewById(R.id.iconImg)


        fun bindItems(item: UserModel) {
            nickname.text = item.nickname
            if(item.icon!=null) {
                Glide.with(itemView).load(item.icon).into(iconImg)
                Log.d("TAG: ", "실행됨")
            }
        }
    }

}
package com.hong.withplanner.recyler_view

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hong.withplanner.R

class PostsAdapter(val context : Context, private val list: MutableList<PostModel>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?
        return when (viewType) {
            PostModel.LOC_TYPE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_post, parent, false)
                LocViewHolder(view)
            }
            PostModel.POST_TYPE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_post2, parent, false)
                PostViewHolder(view)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val obj = list[position]
        when (obj.type) {
            PostModel.LOC_TYPE -> {
                (holder as LocViewHolder).bindItems(obj)
                holder.setIsRecyclable(false)
            }
            PostModel.POST_TYPE -> {
                (holder as PostViewHolder).bindItems(obj)
                holder.setIsRecyclable(false)
            }
        }
    }

    // 여기서 받는 position은 데이터의 index
    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    inner class LocViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nname: TextView = itemView.findViewById(R.id.nickname)
        val icon: ImageView = itemView.findViewById(R.id.iconImg)
        val date: TextView = itemView.findViewById(R.id.date)
        val habit: TextView = itemView.findViewById(R.id.habbit)
        val comment: TextView = itemView.findViewById(R.id.comment)

        fun bindItems(item: PostModel) {
            nname.text = item.post_name
            date.text = item.post_date
            habit.text = item.post_habit
            comment.text = item.post_comment
            Glide.with(itemView).load(item.post_icon).into(icon)
        }
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nname: TextView = itemView.findViewById(R.id.nickname)
        val icon: ImageView = itemView.findViewById(R.id.iconImg)
        val date: TextView = itemView.findViewById(R.id.date)
        val habit: TextView = itemView.findViewById(R.id.habbit)
        val image: ImageView = itemView.findViewById(R.id.image)
        val comment: TextView = itemView.findViewById(R.id.comment)

        fun bindItems(item: PostModel) {
            nname.text = item.post_name
            date.text = item.post_date
            habit.text = item.post_habit
            comment.text = item.post_comment
            Glide.with(itemView).load(item.post_img).into(image)
            Glide.with(itemView).load(item.post_icon).into(icon)
        }
    }
}
package com.shop.withplanner.recyler_view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shop.withplanner.R
import com.shop.withplanner.activity_community.CommunityPostInsideActivity
import kotlin.reflect.typeOf

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

        // 리사이클러뷰 아이템 클릭시 해당 게시물로 이동
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, CommunityPostInsideActivity::class.java)

            // 게시물 정보 넘겨주기
            val postContents = arrayListOf<String>(obj.post_name, obj.post_icon, obj.post_date, obj.post_habit, obj.post_content)
            intent.putExtra("postContents", postContents)
            intent.putExtra("post_type", obj.type)
            if(obj.post_img!=null) {
                intent.putExtra("image", obj.post_img)
            }

            ContextCompat.startActivity(holder.itemView.context, intent, null)
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
        val comment: TextView = itemView.findViewById(R.id.content)

        fun bindItems(item: PostModel) {
            nname.text = item.post_name
            date.text = item.post_date
            habit.text = item.post_habit
            comment.text = item.post_content
            Glide.with(itemView).load(item.post_icon).into(icon)
        }
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nname: TextView = itemView.findViewById(R.id.nickname)
        val icon: ImageView = itemView.findViewById(R.id.iconImg)
        val date: TextView = itemView.findViewById(R.id.date)
        val habit: TextView = itemView.findViewById(R.id.habbit)
        val image: ImageView = itemView.findViewById(R.id.image)
        val comment: TextView = itemView.findViewById(R.id.content)

        fun bindItems(item: PostModel) {
            nname.text = item.post_name
            date.text = item.post_date
            habit.text = item.post_habit
            comment.text = item.post_content
            Glide.with(itemView).load(item.post_img).into(image)
            Glide.with(itemView).load(item.post_icon).into(icon)
        }
    }
}
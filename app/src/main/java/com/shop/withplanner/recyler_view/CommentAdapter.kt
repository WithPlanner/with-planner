package com.shop.withplanner.recyler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.shop.withplanner.R
import com.shop.withplanner.util.RandImg

class CommentAdapter(val commentList: MutableList<CommentModel>): BaseAdapter() {
    override fun getCount(): Int {
        return commentList.size
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if(view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.rv_item_comment, parent, false)
        }

        val iconImg = view?.findViewById<ImageView>(R.id.iconImg)
        val nickname = view?.findViewById<TextView>(R.id.nicknameTextView)
        val comment = view?.findViewById<TextView>(R.id.contentTextView)

        iconImg!!.setImageResource(RandImg.getImg())
        nickname!!.text = commentList[position].nickname
        comment!!.text = commentList[position].comment

        return view!!
    }
}
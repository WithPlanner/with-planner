package com.hong.withplanner.recyler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.hong.withplanner.R

class CategoryAdapter(val categoryList: MutableList<CategoryModel>): BaseAdapter() {
    override fun getCount(): Int {
        return categoryList.size
    }

    override fun getItem(position: Int): Any {
        return categoryList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.lv_item_category, parent, false)
        }
        val category = view?.findViewById<TextView>(R.id.category)
        category!!.text = categoryList[position].category_name

        return view!!
    }

}
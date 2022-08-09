package com.shop.withplanner.recyler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.shop.withplanner.R

class SettingAdapter(val settingList: MutableList<SettingModel>):BaseAdapter() {
    override fun getCount(): Int {
        return settingList.size
    }

    override fun getItem(position: Int): Any {
        return settingList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.lv_item_setting, parent, false)
        }
        val category = view?.findViewById<TextView>(R.id.functionName)
        category!!.text = settingList[position].funtion_name

        return view!!
    }
}
package com.shop.withplanner.recyler_view

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shop.withplanner.R

class LocationAdapter (val itemList: ArrayList<LocationModel>): RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationAdapter.ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_search_location,parent,false)
        return ViewHolder(view)
    }

    //recycler view에 컴포넌트 바인딩
    override fun onBindViewHolder(holder: LocationAdapter.ViewHolder, position: Int) {
        holder.name.text = itemList[position].name
        holder.roadAddress.text = itemList[position].roadAddress
        holder.address.text = itemList[position].address

        //(1)아이템 클릭시 onClick 호출
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it,position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val roadAddress: TextView = itemView.findViewById(R.id.tv_road_address)
        val address: TextView = itemView.findViewById(R.id.tv_address)
    }


    //(2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    //(3) 외부에서 클릭 시 이벤트 설정.
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    //(4) setItemClickListener 함수 실행.
    private lateinit var itemClickListener : OnItemClickListener
}


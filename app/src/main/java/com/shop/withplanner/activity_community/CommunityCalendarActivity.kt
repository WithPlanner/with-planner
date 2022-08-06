package com.shop.withplanner.activity_community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityCalendarBinding
import com.shop.withplanner.recyler_view.ContentsAdapter
import com.shop.withplanner.recyler_view.UserAdapter
import com.shop.withplanner.recyler_view.UserModel

class CommunityCalendarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCommunityCalendarBinding
    private val items =  mutableListOf<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_calendar)

        // 참여자
        for(i in 1..5) {
            items.add(
                UserModel("김수정")
            )
        }

        val rv = binding.recyclerView
        val userAdapter = UserAdapter(this, items)
        rv.adapter = userAdapter
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

//        userAdapter.itemClick = object : ContentsAdapter.ItemClick {
//            override fun onClick(view: View, position: Int) {
//            }
//        }


        binding.backBtn.setOnClickListener{
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
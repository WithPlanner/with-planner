package com.shop.withplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shop.withplanner.community.CommunityCreateActivity
import com.shop.withplanner.databinding.ActivityMyCalendarBinding
import com.shop.withplanner.main.ContentsModel
import com.shop.withplanner.main.RVAdapter

class MyCalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyCalendarBinding
    private val items =  mutableListOf<ContentsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_calendar)

        // 뒤로가기
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        // 설정
        binding.settingBtn.setOnClickListener{
            startActivity(Intent(this, SettingActivity::class.java))
        }
        // 습관모임 만들기
        binding.makeBtn.setOnClickListener{
            startActivity(Intent(this, CommunityCreateActivity::class.java))
        }

        items.add(
            ContentsModel(
                "구움양과 by 런던케이크",
                "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
            )
        )
        items.add(
            ContentsModel(
                "구움양과 by 런던케이크",
                "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
            )
        )
        items.add(
            ContentsModel(
                "구움양과 by 런던케이크",
                "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
            )
        )

        val rv = binding.recyclerView
        val rvAdapter = RVAdapter(this ,items)
        rv.adapter = rvAdapter

        rvAdapter.itemClick = object : RVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
            }
        }

        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
package com.hong.withplanner.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hong.withplanner.MyCalendarActivity
import com.hong.withplanner.R
import com.hong.withplanner.community.CommunityJoinActivity
import com.hong.withplanner.community.CommunityMainLocationActivity
import com.hong.withplanner.community.CommunityMainPostActivity
import com.hong.withplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val myRV_Items =  mutableListOf<ContentsModel>()
    private val forRV_Items =  mutableListOf<ContentsModel>()
    private val hotRV_Items =  mutableListOf<ContentsModel>()
    private val recRV_Items =  mutableListOf<ContentsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // 나중에 서버에서 커뮤니티 정보 받아올 것
        for(i in 1..3) {
            myRV_Items.add(
                ContentsModel(
                    "구움양과 by 런던케이크",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
              )
            )
            forRV_Items.add(
                ContentsModel(
                    "뜨개 by 뜨개왕",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                )
            )
            hotRV_Items.add(
                ContentsModel(
                    "토익 by 100점",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                )
            )
            recRV_Items.add(
                ContentsModel(
                    "미라클모닝 by 굿모닝",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                )
            )
        }
        // 가능하면 함수 합치기
        viewForRV()     // 회원님을 위한 습관모임
        viewMyRV()     // 회원님이 참여하는 습관모임
        viewHotRV()     // 회원님이 참여하는 습관모임
        viewRecRV()     // 회원님이 참여하는 습관모임


        // 마이페이지로
        binding.myBtn.setOnClickListener{
            startActivity(Intent(this, MyCalendarActivity::class.java))
        }


    }
    private fun viewForRV() {
        val rv = binding.forRecyclerView

        val rvAdapter = RVAdapter(this, forRV_Items)
        rv.adapter = rvAdapter

        rvAdapter.itemClick = object : RVAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, CommunityJoinActivity::class.java)
                startActivity(intent)
            }
        }
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
    private fun viewMyRV() {
        val rv = binding.myRecyclerView

        val rvAdapter = RVAdapter(this ,myRV_Items)
        rv.adapter = rvAdapter

        rvAdapter.itemClick = object : RVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, CommunityMainLocationActivity::class.java)
                startActivity(intent)
            }
        }
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
    private fun viewHotRV() {
        val rv = binding.hotRecyclerView

        val rvAdapter = RVAdapter(this ,hotRV_Items)
        rv.adapter = rvAdapter

        rvAdapter.itemClick = object : RVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, CommunityJoinActivity::class.java)
                startActivity(intent)
            }
        }
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
    private fun viewRecRV() {
        val rv = binding.recentRecyclerView

        val rvAdapter = RVAdapter(this ,recRV_Items)
        rv.adapter = rvAdapter

        rvAdapter.itemClick = object : RVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, CommunityJoinActivity::class.java)
                startActivity(intent)
            }
        }
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
}
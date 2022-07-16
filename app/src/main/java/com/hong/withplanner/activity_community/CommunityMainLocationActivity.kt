package com.hong.withplanner.activity_community

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.hong.withplanner.R
import com.hong.withplanner.databinding.ActivityCommunityMainLocationBinding
import com.hong.withplanner.dialog.MyLocDialog
import com.hong.withplanner.recyler_view.PostModel
import com.hong.withplanner.recyler_view.PostsAdapter


class CommunityMainLocationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCommunityMainLocationBinding
    private val postItems =  mutableListOf<PostModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_main_location)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        // 위치인증 버튼
        binding.locAuthBtn.setOnClickListener{
            // 저장된 목적지가 없다면 dlg_my_loc로. 그렇지 않다면 위치인증 화면으로.
            if(true){
                MyLocDialog().show(supportFragmentManager, "목적지 설정")
            }
            else{
                startActivity(Intent(this, CommunityAuthenticateLocationActivity::class.java))
            }
        }

        binding.calendarBtn.setOnClickListener{
            startActivity(Intent(this, CommunityCalendarActivity::class.java))
        }

        binding.arrowBtn.setOnClickListener{
            startActivity(Intent(this, CommunityPostBoardActivity::class.java))
        }

        // 리사이클러뷰
        val destination = "도서관"
        for(i in 1..3) {
            postItems.add(
                PostModel(
                    "수정이",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                    "2022-07-15 00:00:00", "토익공부",
                    "오늘의 습관을 ${destination}에서 완료했어요!", 1, null
                )
            )
        }
        val rv = binding.recyclerView
        val postsAdapter = PostsAdapter(this ,postItems)
        rv.adapter = postsAdapter
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // 구분선
        val decoration = DividerItemDecoration(this, VERTICAL)
        rv.addItemDecoration(decoration)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
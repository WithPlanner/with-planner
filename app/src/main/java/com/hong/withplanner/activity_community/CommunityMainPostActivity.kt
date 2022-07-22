package com.hong.withplanner.activity_community

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hong.withplanner.R
import com.hong.withplanner.databinding.ActivityCommunityMainPostBinding
import com.hong.withplanner.recyler_view.PostModel
import com.hong.withplanner.recyler_view.PostsAdapter


class CommunityMainPostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCommunityMainPostBinding
    private val postItems =  mutableListOf<PostModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_main_post)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.calendarBtn.setOnClickListener{
            startActivity(Intent(this, CommunityCalendarActivity::class.java))
        }

        binding.arrowBtn.setOnClickListener{
            startActivity(Intent(this, CommunityPostBoardActivity::class.java))
        }

        binding.postBtn.setOnClickListener{
            startActivity(Intent(this, CommunityPostActivity::class.java))
        }

        // 리사이클러뷰
        for(i in 1..3) {
            postItems.add(
                PostModel(
                    "수정이",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                    "2022-07-15 00:00:00", "토익공부",
                    "단어 100개 외우기 끝", 2,
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80"
                )
            )
        }
        val rv = binding.recyclerView
        val postsAdapter = PostsAdapter(this ,postItems)
        rv.adapter = postsAdapter
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // 구분선
        val decoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        rv.addItemDecoration(decoration)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
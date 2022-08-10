package com.shop.withplanner.activity_community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityPostBoardBinding
import com.shop.withplanner.recyler_view.PostModel
import com.shop.withplanner.recyler_view.PostsAdapter

class CommunityPostBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityPostBoardBinding
    private val postItems =  mutableListOf<PostModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_post_board)

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

        // Loc 인증이면
//        val destination = "도서관"
//        for(i in 1..10) {
//            postItems.add(
//                PostModel(
//                    "수정이",
//                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
//                    "2022-07-15 00:00:00", "토익공부",
//                    "오늘의 습관을 ${destination}에서 완료했어요!", 1, null
//                )
//            )
//        }

        // Post 인증이면 이게 나오도록 수정 필요
        for(i in 1..10) {
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
package com.shop.withplanner.activity_community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityPostInsideBinding
import com.shop.withplanner.recyler_view.*

class CommunityPostInsideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityPostInsideBinding
    private val commentItems = mutableListOf<CommentModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_post_inside)

        // 게시물 정보 불러오기
        val postContents = intent.getStringArrayListExtra("postContents")
        val postType = intent.getIntExtra("post_type", -1)
        val postImg = intent.getStringExtra("image")

        binding.nickname.text = postContents?.get(0)
        Glide.with(this).load(postContents?.get(1)).into(binding.iconImg)
        binding.date.text = postContents?.get(2)
        binding.habbit.text = postContents?.get(3)
        binding.content.text = postContents?.get(4)

        if(postType == 1) {
            binding.image.visibility = View.GONE
        }
        else {
            binding.image.visibility = View.VISIBLE
            Glide.with(this).load(postImg).into(binding.image)
        }

        // 댓글
        for(i in 1..15) {
            commentItems.add(
                CommentModel("수정이", "멋있어요~"
                ))
        }

        val commentLVAdapter = CommentAdapter(commentItems)
        binding.commentLV.adapter = commentLVAdapter


        // 댓글입력 버튼
        binding.commentBtn.setOnClickListener{
            insertComment()
        }


        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun getCommentData() {



    }

    fun insertComment() {

    }
}
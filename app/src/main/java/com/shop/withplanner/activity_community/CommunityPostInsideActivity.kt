package com.shop.withplanner.activity_community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityPostInsideBinding
import com.shop.withplanner.recyler_view.*

class CommunityPostInsideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityPostInsideBinding
    private val commentItems = mutableListOf<CommentModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_post_inside)


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
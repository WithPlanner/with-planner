package com.shop.withplanner.activity_community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityPostBoardBinding
import com.shop.withplanner.dto.ALlPosts
import com.shop.withplanner.dto.Posts
import com.shop.withplanner.recyler_view.PostModel
import com.shop.withplanner.recyler_view.PostsAdapter
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import com.shop.withplanner.util.RandImg
import retrofit2.Call
import retrofit2.Response

class CommunityPostBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommunityPostBoardBinding
    private val postItems =  mutableListOf<PostModel>()
    private val sharedManager: SharedManager by lazy { SharedManager(this) }
    private lateinit var category : String
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_post_board)

        val intent = intent
        var communityId = intent.getLongExtra("communityId", -1L)
        category = intent.getStringExtra("category").toString()
        type = intent.getStringExtra("communityType").toString()


        RetrofitService.postService.getAllPost(sharedManager.getToken(), communityId).enqueue(
            object : retrofit2.Callback<ALlPosts> {
                override fun onResponse(call: Call<ALlPosts>, response: Response<ALlPosts>) {
                    if(response.isSuccessful) {
                        var result = response.body()!!.result

                        if (result.isNotEmpty()) {
                            makeCard(result)
                        }
                    } else {
                        Log.d("ALLPOST", "onResponse 실패");
                    }
                }
                override fun onFailure(call: Call<ALlPosts>, t: Throwable) {
                    Log.d("ALLPOST", "onFailure 에러: " + t.message.toString());
                }

            }
        )

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun makeCard(posts: List<Posts>) {
        // 리사이클러뷰
        var type_int = -1

        Log.d("type", type)
        if(type=="mapPost") type_int = 1
        else if(type=="post") type_int = 2

        for (post in posts) {
            postItems.add(
                PostModel(
                    post.name,
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                    post.images[0].createdAt, category,
                    post.content, type_int,
                    post.images[0].imgUrl + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80"
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
}
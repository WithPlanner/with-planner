package com.shop.withplanner.activity_community

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityMainPostBinding
import com.shop.withplanner.dto.CommunityPostMain
import com.shop.withplanner.dto.Posts
import com.shop.withplanner.recyler_view.PostModel
import com.shop.withplanner.recyler_view.PostsAdapter
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import com.shop.withplanner.util.Category
import retrofit2.Call
import retrofit2.Response


class CommunityMainPostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCommunityMainPostBinding
    private val postItems =  mutableListOf<PostModel>()
    private val sharedManager: SharedManager by lazy { SharedManager(this) }

    private lateinit var communityName : String
    private lateinit var communityImg : String
    private lateinit var createdAt : String
    private lateinit var updatedAt : String
    private lateinit var introduce : String
    private lateinit var category : String
    private var headCount : Int = 0
    private var currentCount : Int = 0
    private lateinit var days : List<String>
    private lateinit var time : String
    private lateinit var communityType: String
    var communityId: Long = -1L
    var context : Context = this


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this, R.layout.activity_community_main_post)

            communityId = intent.getLongExtra("communityId", -1L)

            RetrofitService.communityService.getPostCommunityMain(sharedManager.getToken(), communityId).enqueue(
                object : retrofit2.Callback<CommunityPostMain> {
                    override fun onResponse(
                        call: Call<CommunityPostMain>,
                        response: Response<CommunityPostMain>
                    ) {
                        if (response.isSuccessful) {
                            var result = response.body()!!.result
                            communityName = result.name
                            communityImg = result.communityImg
                            createdAt = result.createdAt
                            updatedAt = result.updatedAt
                            introduce = result.introduce
                            category = Category.category2string(result.category)
                            headCount = result.headCount
                            currentCount = result.currentCount
                            days = result.days
                            time = result.time
                            communityType = result.type
                            var posts = result.posts

                            Log.d("posts", posts.toString())
                            if (posts.isNotEmpty()) {
                                makeCard(posts)
                            }

                            Glide.with(context)
                                .load(communityImg)
                                .into(binding.mainImg)
                            binding.titleTextView.text = communityName
                            binding.validTextView.text = category
                            binding.teamCountTextView.text = "$currentCount / $headCount"
                            binding.dateTextView.text = createdAt
                            binding.contentDateTextView.text = updatedAt

                            for (day in days) {
                                when (day) {
                                    "월" -> {
                                        binding.mon.visibility = View.VISIBLE
                                    }
                                    "화" -> {
                                        binding.tue.visibility = View.VISIBLE
                                    }
                                    "수" -> {
                                        binding.wed.visibility = View.VISIBLE
                                    }
                                    "목" -> {
                                        binding.thu.visibility = View.VISIBLE
                                    }
                                    "금" -> {
                                        binding.fri.visibility = View.VISIBLE
                                    }
                                    "토" -> {
                                        binding.sat.visibility = View.VISIBLE
                                    }
                                    "일" -> {
                                        binding.sun.visibility = View.VISIBLE
                                    }
                                }
                            }
                            binding.timeTextView.text = time
                            binding.contentTextView.text = introduce
                            binding.contentDateTextView.text = createdAt

                        } else {
                            Log.d("PostCommunityMain", "onResponse 실패");
                        }
                    }

                    override fun onFailure(call: Call<CommunityPostMain>, t: Throwable) {
                        Log.d("PostCommunityMain", "onFailure 에러: " + t.message.toString());
                    }
                }
            )

            binding.backBtn.setOnClickListener {
                onBackPressed()
            }

            binding.calendarBtn.setOnClickListener {
                startActivity(Intent(this, CommunityCalendarActivity::class.java))
            }

            binding.currentPost.setOnClickListener {
                var intent = Intent(this, CommunityPostBoardActivity::class.java)
                intent.putExtra("communityId", communityId)
                intent.putExtra("category", category)
                intent.putExtra("communityType", communityType)
                startActivity(intent)
            }

            binding.postBtn.setOnClickListener {
                var intent = Intent(this, CommunityPostActivity::class.java)
                intent.putExtra("communityId", communityId)
                intent.putExtra("category", category)
                startActivity(intent)
            }
        }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    fun makeCard(posts: List<Posts>) {
        // 리사이클러뷰
        for(post in posts) {
            postItems.add(
                PostModel(
                    post.writerNickname,
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                    post.updatedAt, post.name,
                    post.content, 2,
                    post.images[0].imgUrl + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                    post.postId, communityId
                )
            )
        }
        val rv = binding.recyclerView
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val postsAdapter = PostsAdapter(this ,postItems)
        rv.adapter = postsAdapter

        // 구분선
        val decoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        rv.addItemDecoration(decoration)
    }
}
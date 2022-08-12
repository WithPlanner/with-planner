package com.shop.withplanner.activity_community

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
import com.shop.withplanner.databinding.ActivityCommunityMainLocationBinding
import com.shop.withplanner.dialog.MyLocDialog
import com.shop.withplanner.dto.CommunityMapPostMain
import com.shop.withplanner.dto.MapPosts
import com.shop.withplanner.recyler_view.PostModel
import com.shop.withplanner.recyler_view.PostsAdapter
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import retrofit2.Call
import retrofit2.Response


class CommunityMainLocationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCommunityMainLocationBinding
    private val sharedManager: SharedManager by lazy { SharedManager(this) }
    private val postItems =  mutableListOf<PostModel>()

    var communityId = -1L
    var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_main_location)

        // 선택한 커뮤니티의 communityId 전달받기
        if(intent.hasExtra("communityId")) {
            communityId = intent.getLongExtra("communityId", -1L)
        }

        RetrofitService.communityService.getMapPostCommunityMain(sharedManager.getToken(), communityId).enqueue(
            object : retrofit2.Callback<CommunityMapPostMain> {
                override fun onResponse( call: Call<CommunityMapPostMain>, response: Response<CommunityMapPostMain>) {
                    if(response.isSuccessful) {

                        val community = response.body()!!.result

                        category = community.category

                        binding.titleTextView.text = community.name
                        binding.validTextView.text = category
                        binding.teamCountTextView.text = community.currentCount.toString() + "/" + community.headCount.toString()
                        binding.dateTextView.text = community.createdAt
                        binding.timeTextView.text = community.time
                        binding.contentTextView.text = community.introduce

                        // 최신 게시글
                        val posts = community.mapPosts
                        if(posts.isNotEmpty()) {
                            makeCard(posts)
                        }

                        val image = community.communityImg + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80"
                        // 커뮤니티 이미지
                        if(image != null) {
                            Glide.with(this@CommunityMainLocationActivity).load(image).into(findViewById(R.id.mainImg))
                        }

                        // 습관 요일
                        val days = community.days
                        for(day in days) {
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
                    }
                    else{
                        Log.d("CommunityMainLocationAc", "onResponse 실패")
                    }
                }
                override fun onFailure(call: Call<CommunityMapPostMain>, t: Throwable) {
                    Log.d("CommunityMainLocationAc", "onFailure 에러: " + t.message.toString())
                }
            }
        )
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        // 위치인증 버튼
        binding.locAuthBtn.setOnClickListener{
            // 위치인증 화면으로.
            intent = Intent(this, CommunityAuthenticateLocationActivity::class.java)
            intent.putExtra("communityId", communityId)
            startActivity(intent)
        }

        binding.calendarBtn.setOnClickListener{
            startActivity(Intent(this, CommunityCalendarActivity::class.java))
        }

        binding.currentPost.setOnClickListener{
            intent = Intent(this, CommunityPostBoardActivity::class.java)
            intent.putExtra("communityId", communityId)
            intent.putExtra("category", category)
            intent.putExtra("communityType", "mapPost")
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun makeCard(posts: List<MapPosts>) {
        // 리사이클러뷰
        for(post in posts) {
            postItems.add(
                PostModel(
                    post.nickName,
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                    post.updatedAt, category, "${post.location}에서 오늘의 습관을 완료했어요!", 1
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
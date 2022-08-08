package com.shop.withplanner.activity_community

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityMainPostBinding
import com.shop.withplanner.dto.CommunityPostMain
import com.shop.withplanner.recyler_view.PostModel
import com.shop.withplanner.recyler_view.PostsAdapter
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import retrofit2.Call
import retrofit2.Response


class CommunityMainPostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCommunityMainPostBinding
    private val postItems =  mutableListOf<PostModel>()
    private val sharedManager: SharedManager by lazy { SharedManager(this) }

    private lateinit var communityName : String
    private lateinit var createdAt : String
    private lateinit var introduce : String
    private lateinit var category : String
    private var headCount : Int = 0
    private var currentCount : Int = 0
    private lateinit var days : List<String>
    private lateinit var time : String


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_main_post)

        val idIntent = intent
        var communityId = idIntent.getLongExtra("communityId", -1L)

        RetrofitService.communityService.getPostCommunityMain(sharedManager.getToken(), communityId).enqueue(
            object : retrofit2.Callback<CommunityPostMain> {
                override fun onResponse(
                    call: Call<CommunityPostMain>,
                    response: Response<CommunityPostMain>
                ) {
                    if(response.isSuccessful) {
                        var result = response.body()!!.result
                        Log.d("PostCommunityMain", "onResponse 성공: " + result?.toString());
                        communityId = result.communityId.toLong()
                        communityName = result.name
                        createdAt = result.createdAt
                        introduce = result.introduce
                        category = result.category
                        headCount = result.headCount
                        currentCount = result.currentCount
                        days = result.days
                        time = result.time
                    } else {
                        Log.d("PostCommunityMain", "onResponse 실패: ");
                    }
                }

                override fun onFailure(call: Call<CommunityPostMain>, t: Throwable) {
                    Log.d("PostCommunityMain", "onFailure 에러: " + t.message.toString());
                }

            } )


        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.calendarBtn.setOnClickListener{
            startActivity(Intent(this, CommunityCalendarActivity::class.java))
        }

        binding.currentPost.setOnClickListener{
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
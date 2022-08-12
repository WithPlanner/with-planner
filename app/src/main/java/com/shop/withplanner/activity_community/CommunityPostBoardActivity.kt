package com.shop.withplanner.activity_community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityPostBoardBinding
import com.shop.withplanner.dto.ALlMapPosts
import com.shop.withplanner.dto.ALlPosts
import com.shop.withplanner.dto.MapPosts
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
    private var communityId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_post_board)

        val intent = intent
        communityId = intent.getLongExtra("communityId", -1L)
        category = intent.getStringExtra("category").toString()
        type = intent.getStringExtra("communityType").toString()

        // 게시물 가져오기
        getPosts()

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun getPosts() {
        Log.d("type", type)
        if(type=="post"){
            RetrofitService.postService.getAllPost(sharedManager.getToken(), communityId).enqueue(
                object : retrofit2.Callback<ALlPosts> {
                    override fun onResponse(call: Call<ALlPosts>, response: Response<ALlPosts>) {
                        if(response.isSuccessful) {
                            var result = response.body()!!.result

                            if (result.isNotEmpty()) {
                                makeCard(result, null)
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
        }
        else if(type=="mapPost") {
            RetrofitService.postService.getAllMapPost(sharedManager.getToken(), communityId).enqueue(
                object : retrofit2.Callback<ALlMapPosts> {
                    override fun onResponse(call: Call<ALlMapPosts>, response: Response<ALlMapPosts>) {
                        if(response.isSuccessful) {
                            var result = response.body()!!.result

                            if (result.isNotEmpty()) {
                                makeCard(null, result)
                            }
                        } else {
                            Log.d("ALLPOST", "onResponse 실패");
                        }
                    }
                    override fun onFailure(call: Call<ALlMapPosts>, t: Throwable) {
                        Log.d("ALLPOST", "onFailure 에러: " + t.message.toString());
                    }

                }
            )
        }
        else{
            Toast.makeText(this, "잘못된 게시물 타입.", Toast.LENGTH_SHORT).show()
        }
    }

    fun makeCard(posts: List<Posts>? = null, mapPosts: List<MapPosts>? = null) {
        // 리사이클러뷰: 커뮤니티 타입에 따라 다른 아이템을 넣는다
        var selectedPosts: List<Posts>? = null
        var selectedMapPosts: List<MapPosts>? = null

        if (posts != null) selectedPosts = posts
        else selectedMapPosts = mapPosts

        if (selectedPosts != null) {
            for (post in selectedPosts) {

                var type_int = 2

                postItems.add(
                    PostModel(
                        post.writerNickname,
                        "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                        post.images[0].createdAt, post.name,
                        post.content, type_int,
                        post.images[0].imgUrl + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80"
                    )
                )
            }
        }
        else if(selectedMapPosts != null){
            for (post in selectedMapPosts) {

                var type_int = 1

                postItems.add(
                    PostModel(
                        post.nickName,
                        "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                        post.updatedAt, category,
                        "${post.location}에서 오늘의 습관을 완료했어요!", type_int,
                    )
                )
            }
        }
        val rv = binding.recyclerView
        val postsAdapter = PostsAdapter(this, postItems)
        rv.adapter = postsAdapter
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // 구분선
        val decoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        rv.addItemDecoration(decoration)
    }
}
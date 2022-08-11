package com.shop.withplanner.activity_community

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityJoinBinding
import com.shop.withplanner.dto.CommunityInfo
import com.shop.withplanner.dto.JoinCommunity
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import retrofit2.Call
import retrofit2.Response


class CommunityJoinActivity: AppCompatActivity() {
    private lateinit var binding : ActivityCommunityJoinBinding
    private val sharedManager: SharedManager by lazy { SharedManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_join)

        var communityId = -1L
        var communityType = ""
        var userId = 1

        // 선택한 커뮤니티의 communityId 전달받기
        if(intent.hasExtra("communityId")) {
            communityId = intent.getLongExtra("communityId", -1L)
        }

        // 커뮤니티 정보 GET
        RetrofitService.communityService.getCommunityInfo(sharedManager.getToken(), communityId).enqueue(
            object : retrofit2.Callback<CommunityInfo> {
                override fun onResponse(
                    call: Call<CommunityInfo>, response: Response<CommunityInfo>) {
                    if(response.isSuccessful) {
                        val community = response.body()!!.result
                        binding.titleTextView.text = community.name
                        binding.contentTextView.text = community.introduce
                        binding.dateTextView.text = community.createdAt
                        binding.currentCount.text = community.currentCount.toString()
                        binding.totalCount.text = community.headCount.toString()
                        communityType = community.type

                        // 커뮤니티 이미지가 없으면 기본 이미지로, 있으면 그 이미지로
                        val image = community.communityImg + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80"
                        if(image != null) {
                            Glide.with(this@CommunityJoinActivity).load(image).into(findViewById(R.id.imageView))
                        }
                        Log.d("CommunityJoinActivity", "onResponse 성공" +community?.toString())
                    }
                    else{
                        Log.d("CommunityJoinActivity", "onResponse 실패")
                    }
                }
                override fun onFailure(call: Call<CommunityInfo>, t: Throwable) {
                    Log.d("CommunityJoinActivity", "onFailure 에러: " + t.message.toString())
                }
            }
        )
        binding.closeBtn.setOnClickListener {
            onBackPressed()
        }

        // 가입하기 버튼
        binding.joinBtn.setOnClickListener{
            // 커뮤니티 참여
            RetrofitService.communityService.joinInCommunity(sharedManager.getToken(), communityId).enqueue(
                object : retrofit2.Callback<JoinCommunity> {
                    override fun onResponse(call: Call<JoinCommunity>, response: Response<JoinCommunity>) {
                        if(response.isSuccessful) {
                            val result = response.body()!!.result

                            userId = result.userId
                            Log.d("userId", userId.toString())
                            Log.d("JoinCommunity", "onResponse 성공" +result?.toString())
                        }
                        else{
                            Log.d("JoinCommunity", "onResponse 실패")
                        }
                    }
                    override fun onFailure(call: Call<JoinCommunity>, t: Throwable) {
                        Log.d("JoinCommunity", "onFailure 에러: " + t.message.toString())
                    }
                }
            )

            // 커뮤니티 타입에 따라 다른 액티비티로
            Log.d("communityType", communityType)
            if(communityType == "mapPost") {
                val intent = Intent(this, CommunityMainLocationActivity::class.java)
                intent.putExtra("communityId",38L)
                intent.putExtra("userId", userId)
                startActivity(intent)
            }
            else if (communityType == "post"){
                val intent = Intent(this, CommunityMainPostActivity::class.java)
                intent.putExtra("communityId",38L)
                startActivity(intent)
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
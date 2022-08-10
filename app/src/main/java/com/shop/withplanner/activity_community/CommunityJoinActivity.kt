package com.shop.withplanner.activity_community

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityJoinBinding
import com.shop.withplanner.dto.CommunityPostMain
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
        var communityTpye = ""

        // 선택한 커뮤니티의 communityId 전달받기
        if(intent.hasExtra("communityId")) {
            communityId = intent.getLongExtra("communityId", -1L)
        }

        // 커뮤니티 정보 GET
        RetrofitService.communityService.getPostCommunityMain(sharedManager.getToken(), communityId).enqueue(
            object : retrofit2.Callback<CommunityPostMain> {
                override fun onResponse(
                    call: Call<CommunityPostMain>, response: Response<CommunityPostMain>) {
                    if(response.isSuccessful) {
                        val community = response.body()!!.result
                        binding.titleTextView.text = community.name
                        binding.contentTextView.text = community.introduce
                        binding.dateTextView.text = community.createdAt
                        binding.currentCount.text = community.currentCount.toString()
                        binding.totalCount.text = community.headCount.toString()
                        communityTpye = community.type

                        // 커뮤니티 이미지가 없으면 기본 이미지로, 있으면 그 이미지로
                        val image = community.communityImg + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80"
                        if(image == null) {
                            binding.imageView.setImageResource(R.drawable.ic_launcher_background)
                        }
                        else{
                            Glide.with(this@CommunityJoinActivity).load(image).into(findViewById(R.id.imageView))
                        }
                    }
                    else{
                        Log.d("CommunityJoinActivity", "onResponse 실패")
                    }
                }
                override fun onFailure(call: Call<CommunityPostMain>, t: Throwable) {
                    Log.d("CommunityJoinActivity", "onFailure 에러: " + t.message.toString())
                }
            }
        )
        binding.closeBtn.setOnClickListener{
            onBackPressed()
        }

        // 가입하기 버튼
        binding.joinBtn.setOnClickListener{
            // 커뮤니티 참여
            // 커뮤니티 타입에 따라 다른 액티비티로
            Log.d("communityType", communityTpye)
            if(communityTpye == "mapPost") {
                val intent = Intent(this, CommunityMainLocationActivity::class.java)
                intent.putExtra("communityId",38L)
                startActivity(intent)
            }
            else if (communityTpye == "post"){
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
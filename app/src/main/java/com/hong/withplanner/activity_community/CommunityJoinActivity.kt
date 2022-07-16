package com.hong.withplanner.activity_community

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.R
import com.hong.withplanner.databinding.ActivityCommunityJoinBinding


class CommunityJoinActivity: AppCompatActivity() {
    private lateinit var binding : ActivityCommunityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_join)

        // 서버에서 get할것
        binding.titleTextView.text = "서버: 모임 제목"
        binding.contentTextView.text = "서버: 모임 설명"
        binding.contentTextView.text = "서버: 모임 설명"
        binding.dateTextView.text = "서버: 날짜"
        binding.currentCount.text = "현재 인원"
        binding.totalCount.text = "최대 인원"


        binding.closeBtn.setOnClickListener{
            onBackPressed()
        }

        // 가입하기 버튼
        binding.joinBtn.setOnClickListener{
            // 서버에 유저 정보 보내주고 커뮤니티 정보 받아와서 intent
            // type이 map이면 MainLocation, 아니면 PostActivity로

            val intent = Intent(this, CommunityMainLocationActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
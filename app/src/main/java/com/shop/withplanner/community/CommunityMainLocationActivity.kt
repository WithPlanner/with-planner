package com.shop.withplanner.community

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityMainLocationBinding
import com.shop.withplanner.dialog.MyLocDialog


class CommunityMainLocationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunityMainLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_main_location)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        // 위치인증 버튼
        binding.locAuthBtn.setOnClickListener{
            // 저장된 목적지가 없다면 dlg_my_loc로. 그렇지 않다면 위치인증 화면으로.
            if(true){
                MyLocDialog().show(supportFragmentManager, "목적지 설정")
            }
            else{
                startActivity(Intent(this, CommunityAuthenticateLocationActivity::class.java))
            }
        }

        binding.calendarBtn.setOnClickListener{
            startActivity(Intent(this, CommunityCalendarActivity::class.java))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
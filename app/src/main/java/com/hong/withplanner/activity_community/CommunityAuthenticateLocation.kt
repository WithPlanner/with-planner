package com.hong.withplanner.activity_community

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.R
import com.hong.withplanner.databinding.ActivityCommunityAuthenticateLocationBinding
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.hong.withplanner.activity_etc.MainActivity

class CommunityAuthenticateLocationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunityAuthenticateLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_community_authenticate_location
        )

        binding.findLocBtn.setOnClickListener{

            // 현재위치: 위치 가져와서 표시하기
            binding.currentLoc.text = "현재 위치"
        }

        binding.joinBtn.setOnClickListener{
            // 고정목적지: 서버에서 주소 받아오기(GET)
            binding.destination.text = "고정 목적지"
            // 습관이름 받아오기
            val habitName = "습관이름"

            // 고정위치와 현재위치가 같으면 실행
            // 인증확인 다이얼로그
            intent = Intent(this, MainActivity::class.java)
            val builder = AlertDialog.Builder(this).setTitle(habitName)
                .setMessage("오늘 ${habitName} 인증을 완료했습니다.")
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        startActivity(intent)
                        finish()
                }).show()
        }

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

}
package com.hong.withplanner

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.databinding.ActivityEmailBinding
import com.hong.withplanner.join.JoinActivity

class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email)

        binding.authBtn.setOnClickListener{
            val email = binding.email.text.toString()

            binding.backBtn.setOnClickListener {
                onBackPressed()
            }

            // if문으로 인증 성공시 join 페이지로ㅁㅇ
            // 여기서 사용한 메일주소를 저장했다가 Join페이지 메일주소란에 불러오는건 어떨까

            intent = Intent(this, JoinActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
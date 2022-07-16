package com.hong.withplanner.activity_etc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.EmailActivity
import com.hong.withplanner.R
import com.hong.withplanner.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // 로그인 버튼
        binding.loginBtn.setOnClickListener {

            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if(email.isEmpty()) {
                binding.email.error = "메일주소를 입력해주세요."
            }
            else if(password.isEmpty()) {
                binding.password.error = "비밀번호를 입력해주세요."
            }
            else{
                // 토큰 받아와서 확인하기, 아이디, 비밀번호 찾기, 자동로그인 구현 필요
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

        }
        // 회원가입 버튼
        binding.joinBtn.setOnClickListener{
            startActivity(Intent(this, EmailActivity::class.java))
        }

    }
}
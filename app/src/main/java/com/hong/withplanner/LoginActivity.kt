package com.hong.withplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.databinding.DataBindingUtil
import com.hong.withplanner.databinding.ActivityLoginBinding
import com.hong.withplanner.dto.Token
import com.hong.withplanner.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.hong.withplanner.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    val body = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        // 토큰 받아와서 확인하기, 아이디, 비밀번호 찾기, 자동로그인 구현 필요
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

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

            body.put("email", email)
            body.put("password", password)

            RetrofitService.userService.login(body)?.enqueue(object : Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    if(response.isSuccessful){
                        // 정상적으로 통신이 성고된 경우
                        var result: Token? = response.body()
                        Log.d("LOGIN", "onResponse 성공: " + result?.toString());
                        startActivity(intent)
                    }else{
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d("LOGIN", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d("LOGIN", "onFailure 에러: " + t.message.toString());
                }
            })

        }
        // 회원가입 버튼
        binding.joinBtn.setOnClickListener{
            startActivity(Intent(this, EmailActivity::class.java))
        }

    }
}
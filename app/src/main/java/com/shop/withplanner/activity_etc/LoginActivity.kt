package com.shop.withplanner.activity_etc

import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.databinding.DataBindingUtil
import com.shop.withplanner.EmailActivity
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityLoginBinding
import com.shop.withplanner.dto.LoginDto
import com.shop.withplanner.dto.Token
import com.shop.withplanner.retrofit.RetrofitService

import com.shop.withplanner.shared_preferences.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    val body = HashMap<String, String>()
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        sharedPreference = getSharedPreferences("token", MODE_PRIVATE)

        // 토큰 받아와서 확인하기, 아이디, 비밀번호 찾기, 자동로그인 구현 필요
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // 로그인 버튼
        binding.loginBtn.setOnClickListener {

            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            //로그인 버튼 누를때 워닝 메시지 갱신
            binding.warningEmail.visibility = View.INVISIBLE;
            binding.warningPassword.visibility = View.INVISIBLE;

            if(email.isEmpty()) {
                binding.email.error = "메일주소를 입력해주세요."
            }
            else if(password.isEmpty()) {
                binding.password.error = "비밀번호를 입력해주세요."
            }
            body.put("email", email)
            body.put("password", password)

            RetrofitService.userService.login(body)?.enqueue(object : Callback<LoginDto> {
                override fun onResponse(call: Call<LoginDto>, response: Response<LoginDto>) {
                    if(response.isSuccessful){
                        // 정상적으로 통신이 성공된 경우
                        var result: LoginDto? = response.body()

                        if(result!!.code == 1000){
                            // 토큰 저장
                            val editor  : SharedPreferences.Editor = sharedPreference.edit()
                            editor.putString("token", result.result.jwtToken)
                            editor.commit()

                            binding.warningEmail.visibility = View.GONE;
                            binding.warningPassword.visibility = View.GONE;

                            Log.d("LOGIN", "onResponse 성공: " + result?.toString());
                            startActivity(intent)
                        }
                        else if(result.code == 2007){
                            Log.d("LOGIN실패", "존재하지 않는 이메일입니다.")
                            binding.warningEmail.visibility = View.VISIBLE;
                        }
                        else if(result.code == 2017){
                            Log.d("LOGIN실패", "잘못된 비밀번호 입니다. ")
                            binding.warningPassword.visibility = View.VISIBLE;
                        }

                    }else{
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d("LOGIN", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<LoginDto>, t: Throwable) {
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
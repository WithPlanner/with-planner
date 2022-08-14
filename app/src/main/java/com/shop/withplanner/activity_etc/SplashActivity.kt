package com.shop.withplanner.activity_etc

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.shop.withplanner.R
import com.shop.withplanner.dto.ALlPosts
import com.shop.withplanner.dto.AutoLogin
import com.shop.withplanner.retrofit.RetrofitService

import retrofit2.Call
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPreference = getSharedPreferences("token", MODE_PRIVATE)

        if(sharedPreference.getString("token", null).toString() == null) {
            Handler().postDelayed({
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }, 3000)
        }
        else{
            RetrofitService.userService.autoLogin(sharedPreference.getString("token", null).toString()).enqueue(
                object : retrofit2.Callback<AutoLogin> {
                    override fun onResponse(call: Call<AutoLogin>, response: Response<AutoLogin>) {
                        if(response.isSuccessful) {
                            var result = response.body()!!

                            if(result.code == 1000) {
                                //회원가입이 되어있으므로 MainActivity
                                Handler().postDelayed({
                                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                                    finish()
                                }, 3000)
                            }
                            // 토큰이 유효하지 않음
                            else if (result.code == 2011){
                                // 토큰 삭제
                                val editor = sharedPreference.edit()
                                editor.clear()
                                editor.commit()

                                Handler().postDelayed({
                                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                                    finish()
                                }, 3000)
                            }

                        } else {
                            Log.d("AutoLogin", "onResponse 실패");
                        }
                    }
                    override fun onFailure(call: Call<AutoLogin>, t: Throwable) {
                        Log.d("AutoLogin", "onFailure 에러: " + t.message.toString());
                    }

                }
            )
        }


    }
}
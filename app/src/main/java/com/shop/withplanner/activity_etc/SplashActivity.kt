package com.shop.withplanner.activity_etc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.shop.withplanner.R
import com.shop.withplanner.dto.ALlPosts
import com.shop.withplanner.dto.AutoLogin
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import retrofit2.Call
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    private val sharedManager: SharedManager by lazy { SharedManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(sharedManager.getToken() == null) {
            Handler().postDelayed({
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }, 3000)
        }
        else{
            RetrofitService.userService.autoLogin(sharedManager.getToken()).enqueue(
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
                            else{
                                //회원가입이 안되어있으므로 JoinActivity
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
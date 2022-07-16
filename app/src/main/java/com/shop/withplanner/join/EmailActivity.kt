package com.shop.withplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.R
import com.hong.withplanner.databinding.ActivityEmailBinding
import com.hong.withplanner.dto.EmailAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.shop.withplanner.join.JoinActivity
import com.shop.withplanner.retrofit.RetrofitService

class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        intent = Intent(this, JoinActivity::class.java)

        binding.authBtn.setOnClickListener {
            val email = binding.email.text.toString().trim()

            RetrofitService.userService.checkValidEmail(email)
                ?.enqueue(object : Callback<EmailAuth> {
                    override fun onResponse(call: Call<EmailAuth>, response: Response<EmailAuth>) {
                        if (response.isSuccessful) {
                            var result: EmailAuth? = response.body()
                            if (result?.isSend == true) {
                                Log.d("EMAIL", "onResponse 성공: " + result?.toString());
                                Toast.makeText(
                                    this@EmailActivity,
                                    "이메일을 전송했습니다.",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@EmailActivity,
                                    "이메일 인증에 실패하였습니다.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
//                        if(response.equals(true)){
//                            // if문으로 인증 성공시 join 페이지로
//                            // 여기서 사용한 메일주소를 저장했다가 Join페이지 메일주소란에 불러오는건 어떨까
//                            Log.d("EMAIL", "email 인증 성공")
//
//                            intent.putExtra("email", email)
//                            startActivity(intent)
//                        }

                        } else {
                            Log.d("EMAIL", "onResponse 실패")
                        }
                    }

                    override fun onFailure(call: Call<EmailAuth>, t: Throwable) {
                        Log.d("EMAIL", "onFailure 에러: " + t.message.toString());
                    }
                })
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
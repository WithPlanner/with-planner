package com.shop.withplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.dto.EmailAuth
import com.shop.withplanner.databinding.ActivityEmailBinding
import com.shop.withplanner.dto.AuthNumber
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.shop.withplanner.join.JoinActivity
import com.shop.withplanner.retrofit.RetrofitService

class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding
    private lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        intent = Intent(this, JoinActivity::class.java)

        binding.sendBtn.setOnClickListener {

            email = binding.email.text.toString().trim()

            RetrofitService.userService.checkValidEmail(email)
                ?.enqueue(object : Callback<EmailAuth> {
                    override fun onResponse(
                        call: Call<EmailAuth>,
                        response: Response<EmailAuth>
                    ) {
                        if (response.isSuccessful) {
                            var result: EmailAuth? = response.body()
                            if (result?.isSend == true) {
                                Log.d("EMAIL", "onResponse 성공: " + result?.toString());
                                Toast.makeText(
                                    this@EmailActivity,
                                    "이메일을 전송했습니다.",
                                    Toast.LENGTH_LONG
                                ).show()

                                binding.authNumberLayout.visibility = View.VISIBLE
                                binding.sendBtn.visibility = View.INVISIBLE
                                binding.authBtn.visibility = View.VISIBLE
                            } else {
                                Toast.makeText(
                                    this@EmailActivity,
                                    "이메일 인증에 실패하였습니다.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        } else {
                            Log.d("EMAIL", "onResponse 실패")
                        }
                    }

                    override fun onFailure(call: Call<EmailAuth>, t: Throwable) {
                        Log.d("EMAIL", "onFailure 에러: " + t.message.toString());
                    }
                })
            }

            binding.authBtn.setOnClickListener{
                val authNumber = binding.authNumber.text.toString().trim().toInt()
                RetrofitService.userService.confirmAuthNumber(email, authNumber)
                    ?.enqueue(object : Callback<AuthNumber> {
                        override fun onResponse(
                            call: Call<AuthNumber>,
                            response: Response<AuthNumber>
                        ) {
                            if (response.isSuccessful) {
                                var result: AuthNumber? = response.body()
                                if (result?.isValid == true) {
                                    Log.d("AUTHNUM", "onResponse 성공: " + result?.toString());
                                    intent.putExtra("email", email)
                                    startActivity(intent)

                                    binding.authNumberLayout.visibility = View.VISIBLE
                                } else {
                                    Toast.makeText(
                                        this@EmailActivity,
                                        result?.msg,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            } else {
                                Log.d("AUTHNUM", "onResponse 실패")
                            }
                        }

                        override fun onFailure(call: Call<AuthNumber>, t: Throwable) {
                            Log.d("AUTHNUM", "onFailure 에러: " + t.message.toString());
                        }
                    })
            }
        }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
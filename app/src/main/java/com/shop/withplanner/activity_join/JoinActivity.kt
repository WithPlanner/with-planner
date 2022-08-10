package com.shop.withplanner.activity_join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.R
import com.shop.withplanner.SurveyActivity1
import com.shop.withplanner.activity_etc.LoginActivity
import com.shop.withplanner.databinding.ActivityJoinBinding
import com.shop.withplanner.dto.Result
import com.shop.withplanner.dto.Token
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {
    private lateinit var binding : ActivityJoinBinding
    val body = HashMap<String, String>()
    lateinit var email: String
    lateinit var nickname :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        // 메일인증시 받아온 메일로 EditText 초기화
        email = intent.getStringExtra("email").toString()
        if(email != null){
            binding.email.setText(email)
        }

        intent = Intent(this, LoginActivity::class.java)

        var nicknameChecked = false     // 중복확인여부

        // 뒤로가기 버튼
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        // 닉네임 중복확인
        binding.checkNicknameBtn.setOnClickListener{
            nickname = binding.nickname.text.toString().trim()
            if(nickname.isEmpty()){
                Toast.makeText(this, "입력란을 모두 입력해주세요.", Toast.LENGTH_LONG).show()
                nicknameChecked = false
            } else {
                RetrofitService.userService.checkDupNick(nickname)?.enqueue(object : Callback<Result> {
                    override fun onResponse(call: Call<Result>, response: Response<Result>) {
                        if(response.isSuccessful) {
                            var result: Result? = response.body()
                            nicknameChecked = result!!.result
                            Toast.makeText(this@JoinActivity, result!!.msg, Toast.LENGTH_LONG).show()
                        } else {
                            Log.d("NICKDUP", "onResponse 실패")
                        }
                    }

                    override fun onFailure(call: Call<Result>, t: Throwable) {
                        Log.d("NICKDUP", "onFailure 에러: " + t.message.toString())
                    }
                })
            }
        }

        // 가입하기 버튼 클릭 시 행동
        binding.joinBtn.setOnClickListener{
            val name = binding.name.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val repassword = binding.repassword.text.toString().trim()
            val nickname = binding.nickname.text.toString().trim()

            var readyToJoin = true          // 가입조건 충족여부 확인 변수

            if(name.isEmpty() || password.isEmpty() || repassword.isEmpty() || nickname.isEmpty()){
                Toast.makeText(this, "입력란을 모두 입력해주세요.", Toast.LENGTH_LONG).show()
                readyToJoin = false
            }

            else if(password.length < 6) {
                binding.password.error = "비밀번호는 6글자 이상이어야 합니다."
                readyToJoin = false
            }

            // 비밀번호 2개가 같은지 확인
            else if(password != repassword) {
                binding.repassword.error = "비밀번호가 일치하지 않습니다."
                readyToJoin = false
            }

            else if(!nicknameChecked){
                binding.nickname.error = "닉네임 중복확인을 해주세요."
                readyToJoin = false
            }

            // 조건 모두 충족시 가입 성공
            if(readyToJoin){
                // 서버로 정보 전송
                body.put("email", email)
                body.put("pw", password)
                body.put("name", name)
                body.put("nickname", nickname)

                RetrofitService.userService.signup(body)?.enqueue(object : Callback<Result> {
                    override fun onResponse(call: Call<Result>, response: Response<Result>) {
                        if(response.isSuccessful) {
                            var result: Result? = response.body();
                            Toast.makeText(this@JoinActivity, result!!.msg, Toast.LENGTH_LONG).show()
                            if(result!!.result) {
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        } else {
                            Log.d("JOIN", "onResponse 실패")
                        }
                    }

                    override fun onFailure(call: Call<Result>, t: Throwable) {
                        Log.d("JOIN", "onFailure 에러: " + t.message.toString())
                    }

                })
            }
        }

    }
}
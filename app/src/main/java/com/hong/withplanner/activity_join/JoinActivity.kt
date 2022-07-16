package com.hong.withplanner.activity_join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.R
import com.hong.withplanner.SurveyActivity1
import com.hong.withplanner.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    private lateinit var binding : ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        // 메일인증시 받아온 메일로 EditText 초기화
        val m:String? = intent.getStringExtra("email")
        if(m != null){
            binding.email.setText(m)
        }

        var nicknameChecked = false     // 중복확인여부

        // 뒤로가기 버튼
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        // 닉네임 중복확인
        binding.checkNicknameBtn.setOnClickListener{
            nicknameChecked = true
        }
        // 주소, 우편번호 찾기
        binding.findAddressBtn.setOnClickListener{
            // 찾기로 자동입력시키기
        }

        // 가입하기 버튼 클릭 시 행동
        binding.joinBtn.setOnClickListener{
            val name = binding.name.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val repassword = binding.repassword.text.toString().trim()
            val nickname = binding.nickname.text.toString().trim()
            val zipcode = binding.zipcode.text.toString().trim()
            val address1 = binding.address1.text.toString().trim()
            val address2 = binding.address2.text.toString().trim()

            var readyToJoin = true          // 가입조건 충족여부 확인 변수

            if(name.isEmpty() || password.isEmpty() || repassword.isEmpty()
                || nickname.isEmpty() || zipcode.isEmpty() || address1.isEmpty() || address2.isEmpty()){
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
                // 토큰 받아오기, 주소찾기 구현 필요,
                // 가입 후 이전 액티비티 스택에서 제거(뒤로가기시 회원가입창이 안뜨도록)
                val intent = Intent(this, SurveyActivity1::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

    }
}
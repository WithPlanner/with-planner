package com.hong.withplanner.join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.MainActivity
import com.hong.withplanner.R
import com.hong.withplanner.databinding.ActivityJoinBinding
import kotlinx.android.synthetic.main.activity_join.*

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

        var emailChecked = false                // 중복확인여부
        var nicknameChecked = false

        // 뒤로가기 버튼
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        // 이메일 중복확인
        binding.checkMailBtn.setOnClickListener{

            emailChecked = true
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
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val repassword = binding.repassword.text.toString()
            val nickname = binding.nickname.text.toString()
            val zipcode = binding.zipcode.text.toString()
            val address1 = binding.address1.text.toString()
            val address2 = binding.address2.text.toString()

            var readyToJoin = true          // 가입조건 충족여부 확인 변수

            if(name.isEmpty() || email.isEmpty() || password.isEmpty() || repassword.isEmpty()
                || nickname.isEmpty() || zipcode.isEmpty() || address1.isEmpty() || address2.isEmpty()){
                Toast.makeText(this, "입력란을 모두 입력해주세요.", Toast.LENGTH_LONG).show()
                readyToJoin = false
            }

            else if(password.length < 6) {
                Toast.makeText(this, "비밀번호는 6글자 이상이어야 합니다.", Toast.LENGTH_LONG).show()
                readyToJoin = false
            }

            // 비밀번호 2개가 같은지 확인
            else if(password != repassword) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
                readyToJoin = false
            }
            // 중복확인
            else if(!emailChecked){
                Toast.makeText(this, "이메일 중복확인을 해주세요.", Toast.LENGTH_LONG).show()
                readyToJoin = false
            }
            else if(!nicknameChecked){
                Toast.makeText(this, "닉네임 중복확인을 해주세요.", Toast.LENGTH_LONG).show()
                readyToJoin = false
            }

            // 조건 모두 충족시 가입 성공
            if(readyToJoin){
                // 토큰 받아오기, 주소찾기 구현 필요,
                // 가입 후 이전 액티비티 스택에서 제거(뒤로가기시 회원가입창이 안뜨도록)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
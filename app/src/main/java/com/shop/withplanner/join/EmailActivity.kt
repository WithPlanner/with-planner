package com.shop.withplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.databinding.ActivityEmailBinding
import com.shop.withplanner.join.JoinActivity

class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.authBtn.setOnClickListener {
            val email = binding.email.text.toString().trim()

            // if문으로 인증 성공시 join 페이지로

            intent = Intent(this, JoinActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
package com.hong.withplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    private lateinit var binding : ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
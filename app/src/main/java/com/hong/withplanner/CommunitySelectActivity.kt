package com.hong.withplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.databinding.ActivityCommunitySelectBinding

class CommunitySelectActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunitySelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_select)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_select)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.joinBtn.setOnClickListener{
            startActivity(Intent(this, CommunityMainActivity::class.java))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
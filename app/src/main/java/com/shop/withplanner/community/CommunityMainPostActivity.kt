package com.shop.withplanner.community

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunityMainPostBinding


class CommunityMainPostActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunityMainPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_main_post)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_main_post)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
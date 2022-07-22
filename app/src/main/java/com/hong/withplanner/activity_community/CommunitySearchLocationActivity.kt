package com.hong.withplanner.activity_community

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.R
import com.hong.withplanner.databinding.ActivityCommunitySearchLocationBinding

class CommunitySearchLocationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunitySearchLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_search_location)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_search_location)

        binding.doneBtn.setOnClickListener{
            finish()
        }

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
package com.hong.withplanner.community

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.R
import com.hong.withplanner.databinding.ActivityCommunityAuthenticateLocationBinding

class CommunityAuthenticateLocationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunityAuthenticateLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_authenticate_location)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_community_authenticate_location
        )

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

}
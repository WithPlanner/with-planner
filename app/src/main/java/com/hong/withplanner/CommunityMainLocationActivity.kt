package com.hong.withplanner

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.databinding.ActivityCommunityMainLocationBinding


class CommunityMainLocationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunityMainLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_main_location)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_main_location)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
package com.hong.withplanner

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.databinding.ActivityCommunityCreateBinding
import com.hong.withplanner.databinding.ActivityCommunitySearchLocationBinding
import java.text.SimpleDateFormat
import java.util.*

class CommunitySearchLocationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunitySearchLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_search_location)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_search_location)

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
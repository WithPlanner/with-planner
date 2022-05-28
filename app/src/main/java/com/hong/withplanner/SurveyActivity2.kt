package com.hong.withplanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.databinding.ActivitySurvey2Binding

class SurveyActivity2 : AppCompatActivity() {
    private lateinit var binding : ActivitySurvey2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey2)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_survey2)

        binding.startBtn.setOnClickListener {
            startActivity(Intent(this, CommunityMainLocationActivity::class.java))
        }
    }
}


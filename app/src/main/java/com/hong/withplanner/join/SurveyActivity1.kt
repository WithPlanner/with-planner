package com.hong.withplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.community.CommunityMainLocationActivity
import com.hong.withplanner.databinding.ActivitySurvey1Binding


class SurveyActivity1 : AppCompatActivity() {
    private lateinit var binding : ActivitySurvey1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey1)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_survey1)

        binding.joinBtn.setOnClickListener {
            startActivity(Intent(this, SurveyActivity2::class.java))
        }
    }
}

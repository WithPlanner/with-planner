package com.hong.withplanner

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hong.withplanner.databinding.ActivitySurvey2Binding
import com.hong.withplanner.main.MainActivity

class SurveyActivity2 : AppCompatActivity() {
    private lateinit var binding : ActivitySurvey2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_survey2)

        binding.startBtn.setOnClickListener {
            val questions = listOf<RadioGroup>(binding.question1, binding.question2, binding.question3, binding.question4,
                binding.question5, binding.question6, binding.question7, binding.question8)
            val answers = MutableList(8) {-1}       // 정답값: 제출하지 않은 정답은 -1

            // 설문조사 답 받아오기
            for(i in questions.indices){
                // question1
                if(i==0){
                    val checkedRadioBtnId = questions[i].checkedRadioButtonId
                    if(checkedRadioBtnId!=-1) {
                        val answer = findViewById<RadioButton>(checkedRadioBtnId).text
                        when (answer) {
                            "1학년" -> answers[0] = 1
                            "2학년" -> answers[0] = 2
                            "3학년" -> answers[0] = 3
                            "4학년" -> answers[0] = 4
                        }
                    }
                }
                // question2-8
                else{
                    val checkedRadioBtnId = questions[i].checkedRadioButtonId
                    if(checkedRadioBtnId!=-1) {
                        val answer = findViewById<RadioButton>(checkedRadioBtnId).text
                        when(answer){
                            "예" -> answers[i] = 1       // 예
                            "아니오" -> answers[i] = 0       // 아니오
                        }
                    }
                }
            }

            // 제출시 survey1,2 스택에서 없애는 부분 구현하기
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}


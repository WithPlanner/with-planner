package com.shop.withplanner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shop.withplanner.activity_etc.LoginActivity
import com.shop.withplanner.activity_etc.MainActivity
import com.shop.withplanner.databinding.ActivitySurvey2Binding
import com.shop.withplanner.dto.InvestigationReq
import com.shop.withplanner.dto.Result
import com.shop.withplanner.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SurveyActivity2 : AppCompatActivity() {
    private lateinit var binding : ActivitySurvey2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idIntent = intent
        var userId = idIntent.getLongExtra("userId", -1L)

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
            var requestBody: InvestigationReq = InvestigationReq(
                userId,
                answers[0],
                answers[1],
                answers[2],
                answers[3],
                answers[4],
                answers[5],
                answers[6],
                answers[7]

            )

            RetrofitService.userService.recommendCommunity(requestBody)?.enqueue(object : Callback<Result> {
                override fun onResponse(call: Call<Result>, response: Response<Result>) {
                    if(response.isSuccessful) {
                        var result: Result? = response.body()
                        Toast.makeText(this@SurveyActivity2, result!!.msg, Toast.LENGTH_LONG).show()
                    } else {
                        Log.d("SURVEY", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<Result>, t: Throwable) {
                    Log.d("SURVEY", "onFailure 에러: " + t.message.toString())
                }

            })

            // 제출시 survey1,2 스택에서 없애는 부분 구현하기
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}


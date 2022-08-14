package com.shop.withplanner.activity_etc

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shop.withplanner.R
import com.shop.withplanner.activity_community.CommunityCreateActivity
import com.shop.withplanner.databinding.ActivityMyCalendarBinding
import com.shop.withplanner.dto.CommunityList
import com.shop.withplanner.dto.MyPageInfo
import com.shop.withplanner.recyler_view.ContentsModel
import com.shop.withplanner.recyler_view.ContentsAdapter
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import com.shop.withplanner.util.RandImg
import retrofit2.Call
import retrofit2.Response

class MyCalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyCalendarBinding
    private lateinit var sharedPreference: SharedPreferences
    private val items =  mutableListOf<ContentsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = getSharedPreferences("token", MODE_PRIVATE)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_calendar)

        binding.profileImg.setImageResource(RandImg.getImg())

        RetrofitService.userService.myPageListing(sharedPreference.getString("token", null).toString()).enqueue(
            object : retrofit2.Callback<MyPageInfo> {
                override fun onResponse(call: Call<MyPageInfo>, response: Response<MyPageInfo>) {
                    if (response.isSuccessful) {
                        var result = response.body()?.result
                        binding.profileNickTextView.text = result!!.nickname
                        binding.nicknameTextView.text = result!!.nickname
                        binding.profileIdView.text = result!!.email
                        makeCard(result!!.communities)

                    } else {
                        Log.d("MYPAGE", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<MyPageInfo>, t: Throwable) {
                    Log.d("MYPAGE", "onFailure 에러: " + t.message.toString())
                }

            }
        )

        // 뒤로가기
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        // 설정
        binding.settingBtn.setOnClickListener{
            startActivity(Intent(this, SettingActivity::class.java))
        }
        // 습관모임 만들기
        binding.makeBtn.setOnClickListener{
            startActivity(Intent(this, CommunityCreateActivity::class.java))
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun makeCard(communities: List<CommunityList>) {
        for (community : CommunityList in communities) {
            items.add(
                ContentsModel(
                    community.name,
                    community.communityImg
                )
            )
        }

        val rv = binding.recyclerView
        val contentsAdapter = ContentsAdapter(this ,items)
        rv.adapter = contentsAdapter

        contentsAdapter.itemClick = object : ContentsAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
            }
        }

        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
}
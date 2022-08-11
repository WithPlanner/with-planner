package com.shop.withplanner.activity_etc

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.shop.withplanner.R
import com.shop.withplanner.recyler_view.ContentsModel
import com.shop.withplanner.recyler_view.ContentsAdapter
import com.shop.withplanner.activity_community.CommunityJoinActivity
import com.shop.withplanner.activity_community.CommunityMainPostActivity
import com.shop.withplanner.databinding.ActivityMainBinding
import com.shop.withplanner.dto.CommunityList

import com.shop.withplanner.dto.MainList
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
//import com.shop.withplanner.util.RandImg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val myRV_Items =  mutableListOf<ContentsModel>()
    private val recommendRV_Items =  mutableListOf<ContentsModel>()
    private val hotRV_Items =  mutableListOf<ContentsModel>()
    private val newRV_Items =  mutableListOf<ContentsModel>()
    private val sharedManager: SharedManager by lazy { SharedManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        // 리사이클러뷰 및 인텐트 정의
        val recRV = binding.recommendRecyclerView
        val myRV = binding.myRecyclerView
        val hotRV = binding.hotRecyclerView
        val newRV = binding.newRecyclerView
        val intent_join = Intent(this@MainActivity, CommunityJoinActivity::class.java)
        val intent_mainpost = Intent(this@MainActivity, CommunityMainPostActivity::class.java)

//        binding.myBtn.setImageResource(RandImg.getImg())


        // 커뮤니티 GET 해서 리스팅
        RetrofitService.communityService.mainListing(sharedManager.getToken())
            ?.enqueue(object : Callback<MainList> {
                override fun onResponse(call: Call<MainList>, response: Response<MainList>) {
                    if (response.isSuccessful) {

                        var result: MainList? = response.body()

                        if (result?.isSuccess == true) {
                            var postList = result.result
                            viewCommunityList(recRV, intent_join, recommendRV_Items, postList.recommendList)     // 회원님을 위한 습관모임
                            viewCommunityList(myRV, intent_mainpost, myRV_Items, postList.myList)     // 회원님이 참여하는 습관모임
                            viewCommunityList(hotRV, intent_join, hotRV_Items, postList.hotList)     // 가장 활성화된 습관모임
                            viewCommunityList(newRV, intent_join, newRV_Items, postList.newList)     // 최신 습관모임

                            Log.d("MAIN", "onResponse 성공" + result?.toString())
                        }
                    } else {
                        Log.d("MAIN", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<MainList>, t: Throwable) {
                    Log.d("MAIN", "onFailure 에러: " + t.message.toString())
                }

            })

        // 마이페이지로
        binding.myBtn.setOnClickListener{
            startActivity(Intent(this, MyCalendarActivity::class.java))
        }
    }

    // 커뮤니티 리스팅 함수
    private fun viewCommunityList(rv: RecyclerView, intent: Intent, items: MutableList<ContentsModel>,  posts : List<CommunityList>) {
        for(post in posts) {
            items.add(
                ContentsModel(
                    post.name,
                    post.communityImg + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
//                    post.category
                )
            )
        }
        val contentsAdapter = ContentsAdapter(this, items)
        rv.adapter = contentsAdapter

        contentsAdapter.itemClick = object : ContentsAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                intent.putExtra("communityId", posts[position].communityId.toLong())
                Log.d("Community:", posts[position].communityId.toString())
                startActivity(intent)
            }
        }
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }

    //카카오맵 이용을 위한 해시키 출력 메소드
    fun getHashKey() {
        var packageInfo: PackageInfo = PackageInfo()
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        for (signature: Signature in packageInfo.signatures) {
            try {
                var md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KEY_HASH", "Unable to get MessageDigest. signature = " + signature, e)
            }
        }
    }
}
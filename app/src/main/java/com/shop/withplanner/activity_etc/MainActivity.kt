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
import com.shop.withplanner.dto.Posts
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val myRV_Items =  mutableListOf<ContentsModel>()
    private val forRV_Items =  mutableListOf<ContentsModel>()
    private val hotRV_Items =  mutableListOf<ContentsModel>()
    private val recRV_Items =  mutableListOf<ContentsModel>()

    private val sharedManager: SharedManager by lazy { SharedManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getHashKey();

        RetrofitService.communityService.mainListing(sharedManager.getToken())
            ?.enqueue(object : Callback<MainList> {
                override fun onResponse(call: Call<MainList>, response: Response<MainList>) {
                    if(response.isSuccessful) {
                        var result: MainList? = response.body()
                        if (result?.isSuccess == true) {
                            var postList = result.result
                            // 가능하면 함수 합치기
                            viewForRV(postList.recommendList)     // 회원님을 위한 습관모임
                            viewMyRV(postList.myList)     // 회원님이 참여하는 습관모임
                            viewHotRV(postList.hotList)     // 회원님이 참여하는 습관모임
                            viewRecRV(postList.newList)     // 회원님이 참여하는 습관모임
                            Log.d("MAIN", "onResponse 성공" + result?.toString())
                        }
                    } else {
                        Log.d("MAIN", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<MainList>, t: Throwable) {
                    Log.d("MAIN", "onFailure 에러: " + t.message.toString())
                }

            } )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 마이페이지로
        binding.myBtn.setOnClickListener{
            startActivity(Intent(this, MyCalendarActivity::class.java))
        }


    }
    private fun viewForRV(posts : List<CommunityList>) {
        for (post in posts) {
            forRV_Items.add(
                ContentsModel(
                    post.name,
                    post.communityImg + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                )
            )
        }
        val rv = binding.forRecyclerView

        val contentsAdapter = ContentsAdapter(this, forRV_Items)
        rv.adapter = contentsAdapter

        contentsAdapter.itemClick = object : ContentsAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, CommunityJoinActivity::class.java)
                startActivity(intent)
            }
        }
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
    private fun viewMyRV(posts : List<CommunityList>) {
        for (post in posts) {
            myRV_Items.add(
                ContentsModel(
                    post.name,
                    post.communityImg + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                )
            )
        }
        val rv = binding.myRecyclerView

        val contentsAdapter = ContentsAdapter(this ,myRV_Items)
        rv.adapter = contentsAdapter

        contentsAdapter.itemClick = object : ContentsAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, CommunityMainPostActivity::class.java)
                startActivity(intent)
            }
        }
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
    private fun viewHotRV(posts : List<CommunityList>) {
        for (post in posts) {
            hotRV_Items.add(
                ContentsModel(
                    post.name,
                    post.communityImg + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                )
            )
        }
        val rv = binding.hotRecyclerView

        val contentsAdapter = ContentsAdapter(this ,hotRV_Items)
        rv.adapter = contentsAdapter

        contentsAdapter.itemClick = object : ContentsAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, CommunityJoinActivity::class.java)
                startActivity(intent)
            }
        }
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    }
    private fun viewRecRV(posts : List<CommunityList>) {
        for (post in posts) {
            recRV_Items.add(
                ContentsModel(
                    post.name,
                    post.communityImg + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                )
            )
        }
        val rv = binding.recentRecyclerView

        val contentsAdapter = ContentsAdapter(this ,recRV_Items)
        rv.adapter = contentsAdapter

        contentsAdapter.itemClick = object : ContentsAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, CommunityJoinActivity::class.java)
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
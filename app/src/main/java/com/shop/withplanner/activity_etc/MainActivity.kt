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

import com.shop.withplanner.activity_etc.MyCalendarActivity
import com.shop.withplanner.R
import com.shop.withplanner.recyler_view.ContentsModel
import com.shop.withplanner.recyler_view.ContentsAdapter
import com.shop.withplanner.activity_community.CommunityJoinActivity
import com.shop.withplanner.activity_community.CommunityMainPostActivity
import com.shop.withplanner.databinding.ActivityMainBinding
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
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




        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // 나중에 서버에서 커뮤니티 정보 받아올 것
        for(i in 1..3) {
            myRV_Items.add(
                ContentsModel(
                    "구움양과 by 런던케이크",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
              )
            )
            forRV_Items.add(
                ContentsModel(
                    "뜨개 by 뜨개왕",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                )
            )
            hotRV_Items.add(
                ContentsModel(
                    "토익 by 100점",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                )
            )
            recRV_Items.add(
                ContentsModel(
                    "미라클모닝 by 굿모닝",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                )
            )
        }
        // 가능하면 함수 합치기
        viewForRV()     // 회원님을 위한 습관모임
        viewMyRV()     // 회원님이 참여하는 습관모임
        viewHotRV()     // 회원님이 참여하는 습관모임
        viewRecRV()     // 회원님이 참여하는 습관모임


        // 마이페이지로
        binding.myBtn.setOnClickListener{
            startActivity(Intent(this, MyCalendarActivity::class.java))
        }


    }
    private fun viewForRV() {
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
    private fun viewMyRV() {
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
    private fun viewHotRV() {
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
    private fun viewRecRV() {
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
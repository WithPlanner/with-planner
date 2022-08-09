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
import androidx.core.content.ContextCompat.startActivity
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
import com.shop.withplanner.retrofit.RetrofitService
import com.shop.withplanner.shared_preferences.SharedManager
import retrofit2.Call
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val myRV_Items =  mutableListOf<ContentsModel>()
    private val recommendRV_Items =  mutableListOf<ContentsModel>()
    private val hotRV_Items =  mutableListOf<ContentsModel>()
    private val newRV_Items =  mutableListOf<ContentsModel>()
    private val sharedManager: SharedManager by lazy { SharedManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getHashKey();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // 나중에 서버에서 커뮤니티 정보 받아올 것
        for(i in 1..3) {
            myRV_Items.add(
                ContentsModel(
                    "구움양과 by 런던케이크",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
              )
            )
            recommendRV_Items.add(
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
            newRV_Items.add(
                ContentsModel(
                    "미라클모닝 by 굿모닝",
                    "https://mp-seoul-image-production-s3.mangoplate.com/46651_1630510033594478.jpg?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80",
                )
            )
        }
        // 커뮤니티 리스팅
        RetrofitService.userService.getCommunityList(sharedManager.getToken())?.enqueue(object: Callback<CommunityList> {
            override fun onResponse(call: Call<CommunityList>, response: Response<CommunityList>) {
                if(response.isSuccessful) {
                    val communityList = response.body()
                    Log.d("getCommunityList", communityList.toString())

                }
                else {
                    Log.d("getCommunityList", "onResponse 실패: " + response.errorBody()?.string()!!)
                    sharedManager.getToken()
                }
            }
            override fun onFailure(call: Call<CommunityList>, t: Throwable) {
                Log.d("getCommunityList", "onFailure 에러: " + t.message.toString())
            }
        })
        val recRV = binding.recommendRecyclerView
        val myRV = binding.myRecyclerView
        val hotRV = binding.hotRecyclerView
        val newRV = binding.newRecyclerView
        val intent_join = Intent(this@MainActivity, CommunityJoinActivity::class.java)
        val intent_mainpost = Intent(this@MainActivity, CommunityMainPostActivity::class.java)

        viewCommunityList(recRV, recommendRV_Items, intent_join)     // 회원님을 위한 습관모임
        viewCommunityList(myRV, myRV_Items, intent_mainpost)     // 회원님이 참여하는 습관모임
        viewCommunityList(hotRV, hotRV_Items, intent_join)     // 가장 활성화된 습관모임
        viewCommunityList(newRV, newRV_Items, intent_join)     // 최신 습관모임


        // 마이페이지로
        binding.myBtn.setOnClickListener{
            startActivity(Intent(this, MyCalendarActivity::class.java))
        }
    }

    // 커뮤니티 리스팅 함수
    private fun viewCommunityList(rv: RecyclerView, items: MutableList<ContentsModel>, intent: Intent) {
        val contentsAdapter = ContentsAdapter(this, items)
        rv.adapter = contentsAdapter

        contentsAdapter.itemClick = object : ContentsAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {
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
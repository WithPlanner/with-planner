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
import com.shop.withplanner.activity_community.CommunityMainLocationActivity
import com.shop.withplanner.activity_community.CommunityMainPostActivity
import com.shop.withplanner.databinding.ActivitySearchBinding
import com.shop.withplanner.dto.MainList
import com.shop.withplanner.dto.SearchList
import com.shop.withplanner.recyler_view.ContentsAdapter
import com.shop.withplanner.recyler_view.SearchAdapter
import com.shop.withplanner.recyler_view.SearchModel
import com.shop.withplanner.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private lateinit var sharedPreference: SharedPreferences
    private val search_items = mutableListOf<SearchModel>()
    var searchWord = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        sharedPreference = getSharedPreferences("token", MODE_PRIVATE)

        searchWord = intent.getStringExtra("searchWord").toString()
        binding.searchText.setText(searchWord)


        // 커뮤니티 리스트 가져오기
        RetrofitService.postService.getSearchCommunity(sharedPreference.getString("token", null).toString(), searchWord)
            ?.enqueue(object : Callback<SearchList> {
                override fun onResponse(call: Call<SearchList>, response: Response<SearchList>) {
                    if (response.isSuccessful) {

                        if (response.body()!!.code == 1000) {
                            var result = response.body()!!.result

                            var type = ""
                            var image = ""
                            for(community in result) {
                                if (community.type == "mapPost") type = "위치 인증"
                                else if(community.type == "post") type = "게시글 인증"

                                if(community.communityImg != null) image = community.communityImg + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80"

                                search_items.add(SearchModel(community.name, community.category, type, image))
                            }

                            val rv = binding.searchRecyclerView
                            rv.layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
                            val searchAdapter = SearchAdapter(this@SearchActivity, search_items)
                            rv.adapter = searchAdapter

                            // 클릭시 이동
                            searchAdapter.itemClick = object : ContentsAdapter.ItemClick {
                                override fun onClick(view: View, position: Int) {

                                    var intent = Intent(this@SearchActivity, CommunityMainLocationActivity::class.java)
                                    if(result[position].type == "post") {
                                        intent = Intent(this@SearchActivity, CommunityMainPostActivity::class.java)
                                    }

                                    intent.putExtra("communityId", result[position].communityId.toLong())
                                    startActivity(intent)
                                }
                            }
                        }
                        else{
                            Log.d("Search", "onResponse 실패"+response.body()!!.message)
                        }
                    } else {
                        Log.d("Search", "onResponse 실패")
                    }
                }
                override fun onFailure(call: Call<SearchList>, t: Throwable) {
                    Log.d("Search", "onFailure 에러: " + t.message.toString())
                }
            })

        binding.searchBtn.setOnClickListener{

            searchWord = binding.searchText.text.toString().trim()
            search_items.clear()

            RetrofitService.postService.getSearchCommunity(sharedPreference.getString("token", null).toString(), searchWord)
                ?.enqueue(object : Callback<SearchList> {
                    override fun onResponse(call: Call<SearchList>, response: Response<SearchList>) {
                        if (response.isSuccessful) {

                            if (response.body()!!.code == 1000) {
                                var result = response.body()!!.result

                                var type = ""
                                var image = ""
                                for(community in result) {
                                    if (community.type == "mapPost") type = "위치 인증"
                                    else if(community.type == "post") type = "게시글 인증"

                                    if(community.communityImg != null) image = community.communityImg + "?fit=around|512:512&crop=512:512;*,*&output-format=jpg&output-quality=80"

                                    search_items.add(SearchModel(community.name, community.category, type, image))
                                }

                                val rv = binding.searchRecyclerView
                                rv.layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
                                val searchAdapter = SearchAdapter(this@SearchActivity, search_items)
                                rv.adapter = searchAdapter

                                // 클릭시 이동
                                searchAdapter.itemClick = object : ContentsAdapter.ItemClick {
                                    override fun onClick(view: View, position: Int) {

                                        var intent = Intent(this@SearchActivity, CommunityMainLocationActivity::class.java)
                                        if(result[position].type == "post") {
                                            intent = Intent(this@SearchActivity, CommunityMainPostActivity::class.java)
                                        }

                                        intent.putExtra("communityId", result[position].communityId.toLong())
                                        startActivity(intent)
                                    }
                                }
                            }
                            else{
                                Log.d("Search", "onResponse 실패"+response.body()!!.message)
                            }
                        } else {
                            Log.d("Search", "onResponse 실패")
                        }
                    }
                    override fun onFailure(call: Call<SearchList>, t: Throwable) {
                        Log.d("Search", "onFailure 에러: " + t.message.toString())
                    }
                })
        }
    }
}
package com.shop.withplanner.activity_community

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.shop.withplanner.R
import com.shop.withplanner.databinding.ActivityCommunitySearchLocationBinding
import com.shop.withplanner.dialog.MyLocDialog
import com.shop.withplanner.map.coordToAddress.CoordToAddressApi
import com.shop.withplanner.map.searchKeyword.DtoSearchKeyword
import com.shop.withplanner.map.searchKeyword.KakaoKeywordApiRetrofitClient
import com.shop.withplanner.map.searchKeyword.SearchKeywordApi
import com.shop.withplanner.recyler_view.LocationAdapter
import com.shop.withplanner.recyler_view.LocationModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class CommunitySearchLocationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunitySearchLocationBinding
    private val searchKeywordApi = KakaoKeywordApiRetrofitClient.apiService

    //recyclerView 관련
    private val locationItems = arrayListOf<LocationModel>() //모델(값 정보)
    private val locationAdapter = LocationAdapter(locationItems) //어뎁터
    private var resultName = "" //선택한 장소의 이름.
    private var pageNum = 1 //페이지 번호

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_search_location)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_search_location)

        binding.searchBtn.setOnClickListener{
            val text = binding.location.text.toString()
            pageNum = 1
            searchKeyword(text, pageNum)
        }

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }
        binding.rvLocation.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        binding.rvLocation.adapter = locationAdapter

        //클릭 시 이벤트.
        locationAdapter.setItemClickListener(object: LocationAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                resultName = locationItems[position].name
                var longitude = locationItems[position].longitude
                var latitude = locationItems[position].latitude
                var roadAddress = locationItems[position].roadAddress
                var address = locationItems[position].address

                val nextIntent = Intent(this@CommunitySearchLocationActivity, MyLocDialog::class.java)
                val fragment = MyLocDialog()
                var bundle = Bundle()
                bundle.putString("resultName",resultName)
                bundle.putDouble("longitude",longitude)
                bundle.putDouble("latitude",latitude)
                bundle.putString("roadAddress",roadAddress)
                bundle.putString("address",address)
                fragment.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌.

                fragment.show(supportFragmentManager,"프래그먼트로 전환")
        }} ) }

    private fun searchKeyword(keyword : String, page: Int){
        searchKeywordApi.getSearchKeyword(SearchKeywordApi.API_KEY , keyword, page)
            .enqueue(object : Callback<DtoSearchKeyword>{
                override fun onResponse(
                    call: Call<DtoSearchKeyword>,
                    response: Response<DtoSearchKeyword>
                ) {
                    addLocation(response.body())
                    Log.d("Test 성공 - raw", "Raw: ${response.raw()}")
                    Log.d("Test 성공 - body", "Body: ${response.body()}")
                }
                override fun onFailure(call: Call<DtoSearchKeyword>, t: Throwable) {
                    Log.d("kakaoRetrofit","통신실패.${t.message}")
                }
            }
            )
    }
    //검색결과를 RecyclerView에 담는 함수.
    private fun addLocation(searchResult : DtoSearchKeyword? ){
        if(!searchResult?.documents.isNullOrEmpty()){
            //기존에 있던 리스트 삭제.
            locationItems.clear()

            for (document in searchResult!!.documents) {
                // 결과를 리사이클러 뷰에 추가
                val item = LocationModel(
                    document.place_name,
                    document.road_address_name,
                    document.address_name,
                    document.x.toDouble(), //경도
                    document.y.toDouble() //위도
                )
                locationItems.add(item) //결과 리스트에 add
            }
            locationAdapter.notifyDataSetChanged()
    }}

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
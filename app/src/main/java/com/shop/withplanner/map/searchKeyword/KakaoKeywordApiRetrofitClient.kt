package com.shop.withplanner.map.searchKeyword

import com.shop.withplanner.map.coordToAddress.CoordToAddressApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object KakaoKeywordApiRetrofitClient {
    //retrofit 구성
    private val retrofit : Retrofit.Builder by lazy{
        Retrofit.Builder()
            .baseUrl(SearchKeywordApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }
    val apiService : SearchKeywordApi.SearchKeywordService by lazy{
        retrofit
            .build()
            .create(SearchKeywordApi.SearchKeywordService::class.java)
    }
}

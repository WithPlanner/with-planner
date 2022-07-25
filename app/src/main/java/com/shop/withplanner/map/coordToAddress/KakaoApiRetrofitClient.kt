package com.shop.withplanner.map.coordToAddress

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KakaoApiRetrofitClient {
    private val retrofit : Retrofit.Builder by lazy{
        Retrofit.Builder()
            .baseUrl(CoordToAddressApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }
    val apiService : CoordToAddressApi.CoordToAddressService by lazy{
        retrofit
            .build()
            .create(CoordToAddressApi.CoordToAddressService::class.java)
    }
}

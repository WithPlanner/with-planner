package com.hong.withplanner.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
        //통신할 서버 url
        private const val baseUrl = "http://10.0.2.2:8081"
//        private const val baseUrl = "https://withplanner.shop"

        //Retrofit 객체 초기화
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) //json->gson
            .build()

        val userService: UserService = retrofit.create(UserService::class.java)
    }
}
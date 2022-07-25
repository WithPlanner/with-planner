package com.shop.withplanner.map.coordToAddress

import com.shop.withplanner.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


class CoordToAddressApi {
    companion object {
        const val BASE_URL = "https://dapi.kakao.com/" // HOST
        const val API_KEY = "KakaoAK " + BuildConfig.kakaoMapRestApiKey //HEADER에 들어있어야 하는 Authorizaion키.
    }
    interface CoordToAddressService{
        @GET("v2/local/geo/coord2address.json")
        fun getAddreess(
            @Header("Authorization") key : String,
            @Query("x") x:String, //경도(longitude)
            @Query("y") y:String //위도(latitude)
        ): Call<ResCoordToAddress>
    }
}
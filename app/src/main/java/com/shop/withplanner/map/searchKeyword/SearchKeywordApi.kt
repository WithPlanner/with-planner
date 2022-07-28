package com.shop.withplanner.map.searchKeyword

import com.shop.withplanner.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

class SearchKeywordApi {
    companion object {
        const val BASE_URL = "https://dapi.kakao.com/" // HOST
        const val API_KEY = "KakaoAK "+BuildConfig.kakaoMapRestApiKey //HEADER에 들어있어야 하는 Authorizaion키.
    }
    interface SearchKeywordService{
        @GET("v2/local/search/keyword.json")
        fun getSearchKeyword(
            @Header("Authorization") key : String,
            @Query("query") query : String, //검색어
            @Query("page") page : Int //page(결과 페이지 번호)
        //size(한 페이지에 보여질 문서의 개수) 추후 추가.
        ): Call<DtoSearchKeyword>
    }
}


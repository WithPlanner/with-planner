package com.shop.withplanner.retrofit

import com.shop.withplanner.dto.*
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @Headers("accept: application/json", "content-type: application/json")
    @POST("/login")
    fun login(
        @Body params: HashMap<String, String>,
    ): Call<LoginDto>

    @Headers( "content-type: application/json")
    @POST("/sign_up/check_valid_email")
    fun checkValidEmail(
        @Query("email") email: String
    ): Call<EmailAuth>

    @Headers( "content-type: application/json")
    @POST("/sign_up/check_dup_nickname")
    fun checkDupNick(
        @Query("nickname") nickname: String
    ): Call<Result>

    @Headers( "content-type: application/json")
    @GET("/sign_up/check_valid_email")
    fun confirmAuthNumber(
        @Query("email") email: String,
        @Query("authNumber") authNumber: Int
    ): Call<AuthNumber>

    @Headers("accept: application/json", "content-type: application/json")
    @POST("/sign_up/submit")
    fun signup(
        @Body params: HashMap<String, String>,
    ): Call<UserId>

    @Headers("accept: application/json", "content-type: application/json")
    @POST("/investigation")
    fun recommendCommunity(
        @Body params: InvestigationReq,
    ): Call<Result>

    @Headers("accept: application/json", "content-type: application/json")
    @GET("/mypage")
    fun myPageListing(@Header("X-AUTH-TOKEN") token: String) : Call<MyPageInfo>
}
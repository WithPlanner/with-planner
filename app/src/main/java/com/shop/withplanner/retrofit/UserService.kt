package com.shop.withplanner.retrofit

import com.shop.withplanner.dto.AuthNumber
import com.shop.withplanner.dto.EmailAuth
import com.shop.withplanner.dto.Result
import retrofit2.Call
import com.shop.withplanner.dto.Token
import retrofit2.http.*

interface UserService {
    @Headers("accept: application/json", "content-type: application/json")
    @POST("/login")
    fun login(
        @Body params: HashMap<String, String>,
    ): Call<Token>

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
    ): Call<Result>

    @Headers("accept: application/json", "content-type: application/json")
    @POST("/investigation")
    fun recommendCommunity(
        @Body params: HashMap<String, String>,
    ): Call<Result>
}
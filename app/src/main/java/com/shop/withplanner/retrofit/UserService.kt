package com.shop.withplanner.retrofit

import com.shop.withplanner.dto.AuthNumber
import com.shop.withplanner.dto.EmailAuth
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
    @GET("/sign_up/check_valid_email")
    fun confirmAuthNumber(
        @Query("email") email: String,
        @Query("authNumber") authNumber: Int
    ): Call<AuthNumber>
}
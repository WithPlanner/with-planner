package com.hong.withplanner.retrofit

import com.hong.withplanner.dto.EmailAuth
import com.hong.withplanner.dto.Token
import retrofit2.Call
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
}
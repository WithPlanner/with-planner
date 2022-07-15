package com.shop.withplanner.retrofit

import com.shop.withplanner.dto.Token
import retrofit2.http.*

interface UserService {
    @Headers("accept: application/json", "content-type: application/json")
    @POST("/login")
    fun login(
        @Body params: HashMap<String, String>,
    ): retrofit2.Call<Token>
}
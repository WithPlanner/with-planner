package com.shop.withplanner.retrofit

import com.shop.withplanner.dto.*
import retrofit2.Call
import retrofit2.http.*

interface LocationService {
    @Headers("accept: application/json", "content-type: application/json")
    @POST("/community/loc/search/{communityId}")
    fun sendMyLoc(
        @Header("X-AUTH-TOKEN") token: String,
        @Body myLocReceived: MyLocToSend,
        @Path("communityId") communityId: Long
    ): Call<MyLocToSendResponse>

    @Headers("accept: application/json", "content-type: application/json")
    @GET("/community/loc/user_location/{communityId}")
    fun getMyLoc(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("communityId") communityId: Long
    ): Call<MyLocReceived>

    @Headers("accept: application/json", "content-type: application/json")
    @POST("/community/loc/authenticate/{communityId}")
    fun authenticateLocation(
        @Header("X-AUTH-TOKEN") token: String,
        @Path("communityId") communityId: Long,
        @Body authenticationRequest: AuthenticationRequest,
    ): Call<Authentication>
}
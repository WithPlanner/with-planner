package com.shop.withplanner.retrofit

import com.shop.withplanner.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalTime

interface CommunityService {

    @Multipart
    @POST("make/post")
    fun makePostCommunity(
        @Header("X-AUTH-TOKEN") token : String?,
        @Part communityImg: MultipartBody.Part,
        @Part("name") name : RequestBody,
        @Part("introduce") introduce : RequestBody,
        @Part("category") category : RequestBody,
        @Part("headCount") headCount : RequestBody,
        @Part("day") day : RequestBody,
        @Part("time") time : RequestBody
    ) : Call<MakeCommunity>

    @Headers( "content-type: application/json")
    @GET("/main")
    fun mainListing(
        @Header("X-AUTH-TOKEN") token : String?,
    ) : Call<MainList>

    @Headers( "content-type: application/json")
    @GET("/main")
    fun searchListing(
        @Header("X-AUTH-TOKEN") token : String?,
        @Query("query") query : String
    ) : Call<MainList>

    @Multipart
    @POST("make/loc")
    fun makeMapCommunity(
        @Header("X-AUTH-TOKEN") token : String?,
        @Part communityImg: MultipartBody.Part,
        @Part("name") name : RequestBody,
        @Part("introduce") introduce : RequestBody,
        @Part("category") category : RequestBody,
        @Part("headCount") headCount : RequestBody,
        @Part("day") day : RequestBody,
        @Part("time") time : RequestBody
    ) : Call<MakeCommunity>

    @Headers( "content-type: application/json")
    @GET("community/post/{communityIdx}")
    fun getPostCommunityMain(
        @Header("X-AUTH-TOKEN") token : String?,
        @Path("communityIdx") communityIdx : Long
    ) : Call<CommunityPostMain>
}
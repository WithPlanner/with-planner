package com.shop.withplanner.retrofit

import com.shop.withplanner.dto.MainList
import com.shop.withplanner.dto.Result
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface CommunityService {

    @Headers( "content-type: application/json")
    @GET("/main")
    fun mainListing(
        @Header("X-AUTH-TOKEN") token : String?,
    ) : Call<MainList>

    @Multipart
    @POST("make/post")
    fun makePostCommunity(
        @Header("X-AUTH-TOKEN") token : String?,
        @Part communityImg: RequestBody,
        @Part data: HashMap<String, RequestBody>
    ) : Call<Result>

    @Multipart
    @POST("make/loc")
    fun makeMapCommunity(
        @Header("X-AUTH-TOKEN") token : String?,
        @Part communityImg: RequestBody,
        @Part data: HashMap<String, RequestBody>
    ) : Call<Result>
}
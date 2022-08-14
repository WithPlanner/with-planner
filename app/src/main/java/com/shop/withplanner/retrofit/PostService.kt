package com.shop.withplanner.retrofit

import com.shop.withplanner.dto.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PostService {
    @Multipart
    @POST("/community/post/write/{communityIdx}")
    fun writePost(
        @Header("X-AUTH-TOKEN") token : String?,
        @Path("communityIdx") communityIdx : Long,
        @Part img: MultipartBody.Part,
        @Part("name") name : RequestBody,
        @Part("content") content : RequestBody
    ) : Call<IdAndMsg>

    @GET("/community/post/all/{communityIdx}")
    fun getAllPost(
        @Header("X-AUTH-TOKEN") token : String?,
        @Path("communityIdx") communityIdx : Long
    ) : Call<ALlPosts>

    @GET("/community/map-post/all/{communityIdx}")
    fun getAllMapPost(
        @Header("X-AUTH-TOKEN") token : String?,
        @Path("communityIdx") communityIdx : Long
    ) : Call<ALlMapPosts>

    @GET("/community/post/detail/{postIdx}")
    fun getPostDetail(
        @Header("X-AUTH-TOKEN") token : String?,
        @Path("postIdx") communityIdx : Long
    ) : Call<PostDetail>

    @GET("/community/map-post/detail/{mapPostIdx}")
    fun getMapPostDetail(
        @Header("X-AUTH-TOKEN") token : String?,
        @Path("mapPostIdx") communityIdx : Long
    ) : Call<MapPostDetail>

    @GET("/main/search")
    fun getSearchCommunity(
        @Header("X-AUTH-TOKEN") token : String?,
        @Query("query") query: String
    ) : Call<SearchList>
}
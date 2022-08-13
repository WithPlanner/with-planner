package com.shop.withplanner.retrofit

import com.shop.withplanner.dto.CommentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {
    @POST("/comment/{communityIdx}/{postIdx}")
    fun sendComment(
        @Header("X-AUTH-TOKEN") token : String?,
        @Path("communityIdx") communityIdx : Long,
        @Path("postIdx") postIdx : Int,
        @Body param: HashMap<String, String>
    ): Call<CommentResponse>
}
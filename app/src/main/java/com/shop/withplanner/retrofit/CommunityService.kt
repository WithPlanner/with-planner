package com.shop.withplanner.retrofit

import com.shop.withplanner.dto.Result
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CommunityService {
    @Multipart
    @POST("user/photo")
    fun makeCoummnity(
        @Part communityImg: RequestBody,
        @Part data: HashMap<String, RequestBody>
    ) : Call<Result>
}
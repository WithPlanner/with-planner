package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class InvestigationReq(
    @Expose
    @SerializedName("userIdx")
    var userIdx: Long,
    @Expose
    @SerializedName("q1")
    var q1: Int,
    @Expose
    @SerializedName("q2")
    var q2: Int,
    @Expose
    @SerializedName("q3")
    var q3: Int,
    @Expose
    @SerializedName("q4")
    var q4: Int,
    @Expose
    @SerializedName("q5")
    var q5: Int,
    @Expose
    @SerializedName("q6")
    var q6: Int,
    @Expose
    @SerializedName("q7")
    var q7: Int,
    @Expose
    @SerializedName("q8")
    var q8: Int
)

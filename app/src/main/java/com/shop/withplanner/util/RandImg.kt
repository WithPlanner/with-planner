package com.shop.withplanner.util

import com.shop.withplanner.R

object RandImg {
    var profileImg : List<Int> = listOf(
        R.drawable.default_profile_img_0, R.drawable.default_profile_img_1, R.drawable.default_profile_img_2, R.drawable.default_profile_img_3, R.drawable.default_profile_img_4,
        R.drawable.default_profile_img_4, R.drawable.default_profile_img_5, R.drawable.default_profile_img_6 , R.drawable.default_profile_img_7, R.drawable.default_profile_img_8,
        R.drawable.default_profile_img_9, R.drawable.default_profile_img_10, R.drawable.default_profile_img_11)
    fun getImg(): Int {
        var range = 0..11
        return profileImg.get(range. random())
    }
}
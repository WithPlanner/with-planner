package com.shop.withplanner.recyler_view

class PostModel(
    val post_name: String,
    val post_icon: String,
    val post_date: String,
    val post_title: String,
    val post_content: String,
    val type: Int,       // 게시물 타입
    val post_img: String? = null,
    val post_id: Int,
    val community_id: Long
){
    companion object {
        const val LOC_TYPE = 1          // 위치인증 게시물
        const val POST_TYPE = 2         // 게시물인증 게시물
    }
}
package com.hong.withplanner.util

import java.text.SimpleDateFormat
import java.util.*

class GetTime {
    companion object{

        fun convertTimestampToDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("yyyy년 MM월 dd일 hh:mm", Locale.KOREA)
            val date = sdf.format(timestamp)
            return date
        }
    }
}
package com.shop.withplanner.util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class GetTime {
    companion object{

        fun convertLocalDateTime2String(time: LocalDateTime): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA)
            return sdf.format(time)

        }
    }
}
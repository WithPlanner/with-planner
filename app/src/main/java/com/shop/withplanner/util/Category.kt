package com.shop.withplanner.util

object Category {
    fun category2string(category : String) : String {
        when (category) {
            "miracleMorning" -> return "미라클모닝"
            "communication" -> return ""
            "digitalDetox" -> return "디지털 디톡스"
            "mentalCare" -> return "멘탈관리"
            "exercise" -> return "운동"
            "homeTraining" -> return "홈 트레이닝"
            "stretching" -> return "스트래칭"
            "timeManagement" -> return "시간 관리"
            "hobby" -> return "취미 생활"
            "diet" -> return "다이어트"
            "foreignLanguage" -> return "외국어 공부"
            "writing" -> return "글쓰기 연습 및 필사"
            "nightRoutine" -> return "나이트 루틴"
            "houseClean" -> return "집 정돈"
            "prepareEmployment" -> return "취업 준비"
            "healthHabit" -> return "건강 습관 형성"
            "read" -> return "독서"
            else -> return "오류"
        }
    }

}
package com.shop.withplanner.map.coordToAddress

import android.location.Location

data class DtoCoordToAddress(
    var meta : ResultMeta, //메타 데이터
    var documents : List<AddressData> //검색 결과
)

data class ResultMeta(
    var total_count : Int //검색된 문서 수
)

data class RoadAddress( //도로명주소
    var address_name : String, //전체 도로명 주소
    var region_1depth_name : String, //지역 1Depth, 시도 단위
    var region_2depth_name : String, //지역 2Depth, 구 단위
    var region_3depth_name : String, //지역 3Depth, 면 단위
    var road_name : String, //	도로명
    var underground_yn : String, //지하 여부, Y 또는 N
    var main_building_no : String, //건물 본번
    var sub_building_no : String, //건물 부번, 없을 경우 빈 문자열("") 반환
    var building_name : String, //	건물 이름
    var zone_no : String, //우편번호(5자리)
)
data class Address( //지번주소
    var address_name : String, //전체 지번 주소
    var region_1depth_name : String, //지역 1Depth명, 시도 단위
    var region_2depth_name : String, //지역 2Depth명, 구 단위
    var region_3depth_name : String, //지역 3Depth명, 동 단위
    var mountain_yn : String, //산 여부, Y 또는 N
    var main_address_no : String, //지번 주 번지
    var sub_address_no : String, //지번 부 번지, 없을 경우 빈 문자열("") 반환
)
data class AddressData(
    var road_address : RoadAddress, //도로명 주소
    var Address : Address //지번 주소
)
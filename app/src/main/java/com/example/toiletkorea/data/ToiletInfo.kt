package com.example.toiletkorea.data

import com.firebase.geofire.core.GeoHash
import com.google.gson.annotations.SerializedName

data class ToiletInfo(
    @SerializedName("구분") val type : String?,
    @SerializedName("화장실명") val name : String?,
    @SerializedName("소재지도로명주소") val new_address : String?,
    @SerializedName("소재지지번주소") val old_address : String?,
    @SerializedName("남성용-대변기수") val men_number_of_toilet : Int?,
    @SerializedName("남성용-소변기수") val men_number_of_urinal : Int?,
    @SerializedName("남성용-장애인용대변기수") val men_number_of_disabled_toilet : Int?,
    @SerializedName("남성용-장애인용소변기수") val men_number_of_disabled_urinal : Int?,
    @SerializedName("남성용-어린이용대변기수") val men_number_of_children_toilet : Int?,
    @SerializedName("남성용-어린이용소변기수") val men_number_of_children_urinal: Int?,
    @SerializedName("여성용-대변기수") val women_number_of_toilet : Int?,
    @SerializedName("여성용-장애인용대변기수") val women_number_of_disabled_toilet : Int?,
    @SerializedName("여성용-어린이용대변기수") val women_number_of_children_toilet : Int?,
    @SerializedName("관리기관명") val managed_by : String?,
    @SerializedName("전화번호") val phone_number : String?,
    @SerializedName("개방시간상세") val open_hours : String?,
    @SerializedName("기저귀교환대유무") val diaper : String?,
    @SerializedName("비상벨설치여부") val emergencyAlarm : String?,
    @SerializedName("화장실입구CCTV설치유무") val entrance_CCTV : String?,
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double,
    var geoHash: String,
    )

//data class ToiletInfo(
//    @SerializedName("0") val type : Map<String, String?>?,
//    )
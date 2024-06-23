package com.codeJP.toiletkorea.ui.map

import com.google.gson.annotations.SerializedName

data class OperatingTime(
    @SerializedName("korean") val korean: String,
    @SerializedName("english") val english: String
)

class OperationHourManger {
    private val operatingTimes = listOf(
        OperatingTime("24시간", "24 hours"),
        OperatingTime("연중무휴", "Always open"),
        OperatingTime("상시", "Always open"),
        OperatingTime("8시간", "8 hours"),
        OperatingTime("9시간", "9 hours"),
        OperatingTime("영업시간", "Within business hours"),
        OperatingTime("(평일)09:00~18:00", "Weekdays 09:00~18:00"),
        OperatingTime("24", "24 hours"),
        OperatingTime("11시간", "11 hours"),
        OperatingTime("근무시간", "Within business hours"),
        OperatingTime("정시(09:00∼18:00)", "Regular hours (09:00~18:00)"),
        OperatingTime("(평일)09:00-18:00", "Weekdays 09:00-18:00"),
        OperatingTime("12시간", "12 hours"),
        OperatingTime("09~18", "09~18"),
        OperatingTime("9:00~18:00", "9:00~18:00"),
        OperatingTime("정시", "Regular hours"),
        OperatingTime("24시", "24 hours"),
        OperatingTime("평일 09:00~18:00", "Weekdays 09:00~18:00"),
        OperatingTime("근무시간 내", "Within business hours"),
        OperatingTime("영업시간 내", "Within business hours"),
        OperatingTime("9시~18시", "09:00~18:00"),
        OperatingTime("0900-1800", "09:00-18:00"),
        OperatingTime("16시간", "16 hours"),
        OperatingTime("운영시간내", "Within operating hours"),
        OperatingTime("13시간", "13 hours"),
        OperatingTime("(09:00~18:00)", "09:00~18:00"),
        OperatingTime("00~24", "24 hours"),
        OperatingTime("영업시간중", "Within business hours"),
        OperatingTime("미개방", "Not open"),
        OperatingTime("(평일)09:00~18:00+(주말)09:00~18:00+(공휴일)09:00~18:00", "09:00~18:00"),
        OperatingTime("09:00 ~ 18:00", "09:00~18:00"),
        OperatingTime("05~24", "05:00~24:00"),
        OperatingTime("업무시간", "Within business hours"),
        OperatingTime("14시간", "14 hours"),
        OperatingTime("상시개방", "Always open"),
        OperatingTime("9", "9 hours"),
        OperatingTime("09:00~19:00", "09:00~19:00"),
        OperatingTime("18시간", "18 hours"),
        OperatingTime("10시간", "10 hours"),
        OperatingTime("09~18시", "09:00~18:00"),
        OperatingTime("24시간 개방", "24 hours open"),
        OperatingTime("17시간", "17 hours"),
        OperatingTime("09-18시", "09~18:00"),
        OperatingTime("00시-24시", "24 hours"),
        OperatingTime("(09:00∼18:00)", "09:00~18:00"),
        OperatingTime("공항운영시간", "Airport operating hours"),
        OperatingTime("09시~18시", "09:00~18:00"),
        OperatingTime("8", "8 hours"),
        OperatingTime("(09:00∼20:00)", "09:00~20:00"),
        OperatingTime("0900-2400", "09:00~24:00"),
        OperatingTime("개방시간내", "Within opening hours"),
        OperatingTime("09:00~18:00 주말및공휴일 미개방", "09:00~18:00 (Not open on weekends and holidays)"),
        OperatingTime("10", "10 hours"),
        OperatingTime("9시-18시", "09:00~18:00"),
        OperatingTime(
            "(평일)09:00~18:00+(주말)이용불가+(공휴일)이용불가",
            "09:00~18:00 (Not open on weekends and holidays)"
        ),
        OperatingTime("정시(09:00~18:00)", "09:00~18:00"),
        OperatingTime("08:00~19:00", "08:00~19:00"),
        OperatingTime("영업중", "Within business hours"),
        OperatingTime("주5일", "Only Weekdays"),
        OperatingTime("00:00 ~ 00:00", "24 hours"),
        OperatingTime("9시간(09:00~18:00)", "09:00~18:00"),
        OperatingTime("근무시간내", "Within business hours"),
        OperatingTime("20시간", "20 hours"),
        OperatingTime("근무중", "Within business hours"),
        OperatingTime("하절기(7~9월)", "Summer season (July to September)"),
        OperatingTime("15시간", "15 hours"),
        OperatingTime("평일 정시(09:00~18:00)", "Weekdays 09:00~18:00"),
        OperatingTime("공연시", "During performances"),
        OperatingTime("6시간", "6 hours"),
        OperatingTime("12", "12 hours"),
        OperatingTime("19시간", "19 hours"),
        OperatingTime("영업시간내", "Within business hours"),
        OperatingTime("(평일)09:00~16:00", "Weekdays 09:00~16:00")
    )

    // 운영시간을 영어로 변환하는 함수
    fun TranslateOperatingTime(time: String): String {
        val operatingTime = operatingTimes.find { it.korean == time }
        return operatingTime?.english ?: "Not listed"
    }

    fun FilterOperationHours(time: String): String {
        val firstPattern = Regex("^([01]?[0-9]|2[0-3]):[0-5][0-9]~([01]?[0-9]|2[0-3]):[0-5][0-9]$")
        val secondPattern = Regex("^([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]$")

        return when {
            operatingTimes.any { it.korean == time } -> TranslateOperatingTime(time)
            firstPattern.matches(time) -> time
            secondPattern.matches(time) -> time
            else -> "No operation info"
        }
    }
}


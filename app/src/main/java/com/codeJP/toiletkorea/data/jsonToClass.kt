package com.codeJP.toiletkorea.data

import android.content.Context
import android.util.Log
import com.codeJP.toiletkorea.TAG
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream

fun jsonToDataClass(context: Context, resourceId: Int)
        : MutableMap<String, ToiletInfo> {
    val JsonData = resourceId
    val inputStream: InputStream = context.resources.openRawResource(JsonData)
    val json = inputStream.bufferedReader().use { it.readText() }
    val type = object : TypeToken<Map<String, ToiletInfo>>() {}.type
    val toiletInfoMap: MutableMap<String, ToiletInfo> = Gson().fromJson(json, type)
    toiletInfoMap.forEach { (key, toiletInfo) ->
        Log.d(TAG, "Key: $key, Old ToiletInfo: ${toiletInfo}")
        val lat = toiletInfo.latitude
        val lng = toiletInfo.longitude
        val hash = GeoFireUtils.getGeoHashForLocation(GeoLocation(lat, lng))
        toiletInfo.geoHash = hash
        Log.d(TAG, "Key: $key, New ToiletInfo: ${toiletInfo}")
    }
    Log.d(TAG, toiletInfoMap["0"]!!.javaClass.kotlin.toString())

    return toiletInfoMap
}
package com.codeJP.toiletkorea.data

import android.content.Context
import android.util.Log
import com.codeJP.toiletkorea.TAG
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


fun write(context: Context) {
//    val citiesEngForFirebase = listOf(
//        "Seoul", "Busan", "Daegu", "Jeollanam-do",
//        "Daejeon", "Incheon", "Ulsan", "Jeju-do", "Gyeonggi-do", "Jeollabuk-do", "Chungcheongbuk-do",
//        "Gyeongsangbuk-do", "Gangwon-do", "Gyeongsangnam-do", "Chungcheongnam-do", "Sejong-si"
//    )
//    val citiesEng = listOf(
//        "seoul", "busan", "daegu", "jeollanam_do",
//        "daejeon", "incheon", "ulsan", "jeju_do", "gyeonggi_do", "jeollabuk_do", "chungcheongbuk_do",
//        "gyeongsangbuk_do", "gangwon_do", "gyeongsangnam_do", "chungcheongnam_do", "sejong_si"
//    )
    val citiesEngForFirebase = listOf("Jeollanam-do")
    val citiesEng = listOf("gwangju")

    val ResourceIdList = mutableListOf<Int>()

    citiesEng.forEach { city ->
        val jsonDataResourceId = context.resources.getIdentifier(city, "raw", context.packageName)
        ResourceIdList.add(jsonDataResourceId)
    }

    Log.d(TAG, ResourceIdList.toString())

    for ((id: Int, city: String) in ResourceIdList.zip(citiesEngForFirebase)) {

        if (city == "Seoul" || city == "Incheon") {
            continue
        } else {
            val db = Firebase.firestore
            val toiletInfoMap = jsonToDataClass(context, id)
            toiletInfoMap.forEach { (key, toiletInfo) ->
                db.collection(city).document(key).set(toiletInfoMap[key]!!)
                    .addOnSuccessListener {
                        Log.d(TAG, "데이터 추가 성공")
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "데이터 추가 실패", exception)
                    }
            }
        }
    }
}


fun read() {
    val db = Firebase.firestore

    db.collection("users")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}


suspend fun readToiletInfoFromDB(
    context: Context,
    latitude: Double,
    longitude: Double
): MutableList<DocumentSnapshot> {
    val db = Firebase.firestore

    // Find cities within 5km
    val center = GeoLocation(latitude, longitude)
    val radiusInM = 4 * 1000.0

    val path: String = reverseGeocode(context = context, latitude = latitude, longitude = longitude)
        ?: "No matching city"

// Each item in 'bounds' represents a startAt/endAt pair. We have to issue
// a separate query for each pair. There can be up to 9 pairs of bounds
// depending on overlap, but in most cases there are 4.
    val bounds = GeoFireUtils.getGeoHashQueryBounds(center, radiusInM)
    val tasks: MutableList<Task<QuerySnapshot>> = ArrayList()
    Log.d(TAG, "bounds: $bounds")
    for (b in bounds) {
        val q = db.collection(path)
            .orderBy("geoHash")
            .startAt(b.startHash)
            .endAt(b.endHash)
        tasks.add(q.get())
    }


    return suspendCancellableCoroutine { continuation ->
// Collect all the query results together into a single list
        val matchingToiletInfo: MutableList<DocumentSnapshot> = ArrayList()

        Tasks.whenAllComplete(tasks)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (task in tasks) {
                        val snap = task.result
                        for (doc in snap!!.documents) {
                            val lat = doc.getDouble("latitude")!!
                            val lng = doc.getDouble("longitude")!!

                            // We have to filter out a few false positives due to GeoHash
                            // accuracy, but most will match
                            val docLocation = GeoLocation(lat, lng)
//                    Log.d(TAG, "docLocation: $docLocation" )
                            val distanceInM = GeoFireUtils.getDistanceBetween(docLocation, center)
                            if (distanceInM <= radiusInM) {
                                matchingToiletInfo.add(doc)
//                        Log.d(TAG, "doc: ${doc.data}")
                            }
                        }
                    }

                    Log.d(TAG, "matchDocs: ${matchingToiletInfo.size}")
                    continuation.resume(matchingToiletInfo)
                } else {
                    continuation.resumeWithException(task.exception ?: Exception("Unknown error"))
                }
            }

    }


}

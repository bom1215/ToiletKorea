package com.codeJP.toiletkorea.data

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.codeJP.toiletkorea.TAG
import kotlinx.coroutines.time.delay
import java.io.IOException
import java.util.Locale

private const val MAX_RETRY_COUNT = 3

fun reverseGeocode(context: Context, latitude: Double, longitude: Double): String? {
    var retryCount = 0
    var result: String? = null
    while (retryCount < MAX_RETRY_COUNT) {
        try {
            val koreaLocale = Locale("en", "kr")
            if (Build.VERSION.SDK_INT < 33) { // SDK 버전이 33보다 큰 경우에만 아래 함수를 씁니다.
                val geocoder = Geocoder(context, koreaLocale)
                val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
                addresses?.firstOrNull()?.let {
                    result = it.adminArea // 예시 : 서울특별시
                }
            } else { //
                val geocoder = Geocoder(context, Locale.getDefault())
                var address: Address? = null
                val geocodeListener = @RequiresApi(33) object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        // 주소 리스트를 가지고 할 것을 적어주면 됩니다.
                        address = addresses[0];
                        address?.let {
                            result = "${it.adminArea}"
                        }
                    }

                    override fun onError(errorMessage: String?) {
                        Log.d(TAG, "발견된 주소가 없습니다. ")
                        address = null
                        result = address
                    }
                }
                geocoder.getFromLocation(latitude, longitude, 1, geocodeListener)
            }
            if (!result.isNullOrEmpty()) {
                break
            }
        } catch (e: IOException) {
            Log.e("GeocodeError", "Geocoding failed on attempt $retryCount", e)
        }
        // 재시도 간격(예: 1초)을 두고 다시 시도합니다.
        retryCount++
    }
    return result
}

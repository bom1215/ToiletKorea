package com.codeJP.toiletkorea

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.codeJP.toiletkorea.ui.theme.ToiletKoreaTheme
import dagger.hilt.android.AndroidEntryPoint


public val TAG: String = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            ToiletKoreaTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    val context = LocalContext.current
                    ToiletKoreaApp()
//                    val Intent = Intent(this, EmailPasswordActivity::class.java)
//                    startActivity(Intent)

//                    val filePath = context.filesDir
//                    basicReadWrite(filePath)
//                    practice(filePath)
//                    read()
//                    write(context)
//                    jsonToDataClass(context)
//                    readToiletInfoFromDB(latitude = 37.528643684, longitude = 127.126365737)

                }
            }
        }
    }

    internal fun areLocationPermissionsAlreadyGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun openApplicationSettings() {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        ).also {
            startActivity(it)
        }
    }

    internal fun decideCurrentPermissionStatus(
        locationPermissionsGranted: Boolean,
        shouldShowPermissionRationale: Boolean
    ): String {
        return if (locationPermissionsGranted) "Granted"  //승인
        else if (shouldShowPermissionRationale) "Rejected"  //거절
        else "Denied" // 회피
    }

    internal fun moveToAnotherMapAPP(newAddress: String) {
        val location = Uri.parse("geo:0,0?q=$newAddress")
        val mapIntent = Intent(Intent.ACTION_VIEW, location)
// Try to invoke the intent.
        try {
            Log.d(TAG, "지도 앱 실행")
            startActivity(mapIntent)
        } catch (e: ActivityNotFoundException) {
            Log.d(TAG, "ActivityNotFoundException: $e")
            // Define what your app should do if no activity can handle the intent.
        }
    }
}


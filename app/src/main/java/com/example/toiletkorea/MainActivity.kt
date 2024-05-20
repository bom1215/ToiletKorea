package com.example.toiletkorea

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.toiletkorea.data.jsonToDataClass
import com.example.toiletkorea.data.read
import com.example.toiletkorea.data.readToiletInfoFromDB
import com.example.toiletkorea.data.write
import com.example.toiletkorea.ui.theme.ToiletKoreaTheme


public val TAG : String = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToiletKoreaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ToiletKoreaApp()
                    val context = LocalContext.current
                    val filePath = context.filesDir
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
            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun openApplicationSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)).also {
            startActivity(it)
        }
    }

    internal fun decideCurrentPermissionStatus(locationPermissionsGranted: Boolean,
                                               shouldShowPermissionRationale: Boolean): String {
        return if (locationPermissionsGranted) "Granted"  //승인
        else if (shouldShowPermissionRationale) "Rejected"  //거절
        else "Denied" // 회피
    }
}





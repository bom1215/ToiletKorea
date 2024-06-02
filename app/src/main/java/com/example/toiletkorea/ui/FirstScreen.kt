package com.example.toiletkorea.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.toiletkorea.MainActivity
import com.example.toiletkorea.R
import com.example.toiletkorea.TAG
import com.example.toiletkorea.ToiletScreen
import com.example.toiletkorea.ui.map.LocationPermissionRequest
import kotlinx.coroutines.delay

@Composable
fun FirstScreen(
    activity: MainActivity,
    navController: NavController
) {
    Log.d(TAG, "첫 화면 시작")

    Scaffold{Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(id = R.drawable.main_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(170.dp)
        )
    }}
    if (activity.areLocationPermissionsAlreadyGranted()){
        LaunchedEffect(Unit) {
            delay(3000)
            Log.d(TAG, "로그인 화면 전환")
            navController.navigate(ToiletScreen.LoginMain.name)
        }
    }
    else {
        // 백그라운드 스레드에서 위치 권한 요청을 처리합니다.
        LocationPermissionRequest(
            activity,
            navController)
    }
}

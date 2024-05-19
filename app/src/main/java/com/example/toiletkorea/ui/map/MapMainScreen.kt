package com.example.toiletkorea.ui.map

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toiletkorea.MainActivity
import com.example.toiletkorea.TAG



@Composable
fun MapMainScreen(
    activity: MainActivity,
    mapViewModel: MapViewModel = viewModel()) {

    val MapUiState by mapViewModel.uiState.collectAsState()
    Scaffold (
        snackbarHost = { SnackbarHost(hostState = MapUiState.snackbarHostState) },
    ) {
        Column (modifier = Modifier.padding(it)) {
            Log.d(TAG, "구글맵 작동 시작.")
            GoogleMapScreen(activity)
        }

    }
}

//@Composable
//fun MapMainScreen(activity: MainActivity) {
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    Scaffold (
//        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
//        ) {
//        Column (modifier = Modifier.padding(it)) {
//            Log.d(TAG, "구글맵 작동 시작.")
//            GoogleMapScreen(activity)
//        }
//
//    }
//}
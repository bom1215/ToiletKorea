package com.example.toiletkorea.ui.map

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.toiletkorea.MainActivity
import com.example.toiletkorea.TAG
import java.lang.RuntimeException


@Composable
fun MapMainScreen(
    activity: MainActivity,
    mapViewModel: MapViewModel = viewModel()) {

    val mapUiState by mapViewModel.uiState.collectAsState()
    Scaffold (
        snackbarHost = { SnackbarHost(hostState = mapUiState.snackbarHostState) },
    ) {
        Column (modifier = Modifier.padding(it)) {
            Log.d(TAG, "구글맵 작동 시작.")
            GoogleMapScreen()
        }

    }
}
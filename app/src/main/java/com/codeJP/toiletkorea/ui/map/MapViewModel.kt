package com.codeJP.toiletkorea.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.codeJP.toiletkorea.TAG
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await


data class MapUiState(
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    val matchingToiletInfo: List<DocumentSnapshot> = emptyList(),
    val clickedMarkerInfo: DocumentSnapshot? = null,
    val showBottomSheet: Boolean = false,
    val clickedMarker: MarkerState? = null,
)

class MapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()


    fun updateShowBottomSheet(show: Boolean) {
        _uiState.value = _uiState.value.copy(showBottomSheet = show)
    }

    fun updateClickedMarker(marker: MarkerState) {
        _uiState.value = _uiState.value.copy(clickedMarker = marker)
    }

    fun updateClickedMarkerInfo(markerInfo: DocumentSnapshot?) {
        _uiState.value = _uiState.value.copy(clickedMarkerInfo = markerInfo)
    }

    fun updateMatchingToiletInfo(matchingToiletInfo: List<DocumentSnapshot>) {
        _uiState.value = _uiState.value.copy(matchingToiletInfo = matchingToiletInfo)
    }

    suspend fun getLastKnownLocation(context: Context): LatLng? {
        try {
//            var location : LatLng = LatLng(30.0,0.0)
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val fusedLocationProviderClient: FusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context)
                val task =
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener { }.await()
                return LatLng(task.latitude, task.longitude)
            } else return null
        } catch (e: Exception) {
            Log.d(TAG, "error occured", e)
            return null
        }
    }

}


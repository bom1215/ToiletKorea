package com.example.toiletkorea.ui.map

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import com.example.toiletkorea.R
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.firestore.DocumentSnapshot
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class MapUiState (
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
    val matchingToiletInfo: List<DocumentSnapshot> = emptyList(),
    val clickedMarkerInfo: DocumentSnapshot? = null,
    val showBottomSheet: Boolean = false,
)
class MapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()


    fun updateShowBottomSheet(show: Boolean) {
        _uiState.value = _uiState.value.copy(showBottomSheet = show)
    }
    fun updateClickedMarkerInfo(markerInfo : DocumentSnapshot?){
        _uiState.value = _uiState.value.copy(clickedMarkerInfo = markerInfo)
    }

    fun updateMatchingToiletInfo(matchingToiletInfo: List<DocumentSnapshot>){
        _uiState.value = _uiState.value.copy(matchingToiletInfo = matchingToiletInfo )
    }

}


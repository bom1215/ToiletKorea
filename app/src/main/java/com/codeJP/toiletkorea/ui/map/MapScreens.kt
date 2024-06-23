package com.codeJP.toiletkorea.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codeJP.toiletkorea.TAG
import com.codeJP.toiletkorea.data.readToiletInfoFromDB
import com.codeJP.toiletkorea.ui.theme.ToiletKoreaTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.firebase.firestore.DocumentSnapshot
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


var tempMarkerInfo: DocumentSnapshot? = null

@Composable
fun GoogleMapScreen(
    mapViewModel: MapViewModel = viewModel()
) {
    val mapUiState by mapViewModel.uiState.collectAsState()
    val context = LocalContext.current

    var startLocation = LatLng(37.528643684, 127.126365737)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startLocation, 15f)
    }

    // 자동으로 현재 위치로 지도 수정
    LaunchedEffect(Unit) {
        val location: LatLng? = mapViewModel.getLastKnownLocation(context)
        startLocation =
            LatLng(location?.latitude ?: 37.528643684, location?.longitude ?: 127.126365737)
        val cameraPosition = CameraPosition.fromLatLngZoom(startLocation, 15f)
        cameraPositionState.move(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    val southWest = LatLng(33.241587, 125.189360) // 예: 한국의 남서쪽 경계
    val northEast = LatLng(38.50212, 130.49490) // 예: 한국의 북동쪽 경계

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoomPreference = 21f,
                minZoomPreference = 5f,
                isMyLocationEnabled = true,
                latLngBoundsForCameraTarget = LatLngBounds(southWest, northEast)
            )
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                mapToolbarEnabled = false,
                myLocationButtonEnabled = true,
                scrollGesturesEnabled = true,
                zoomGesturesEnabled = true,
                compassEnabled = true

            )
        )
    }

    val onMyLocationButtonClick: (() -> Boolean) = {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationProviderClient: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener {
                val cameraPosition =
                    CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 15f)
                cameraPositionState.move(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
            true
        } else {
            false
        }
    }
    val threshold = 0.01 // 예시로 임계값 설정 (약 5km)
    var lastLatitude by remember { mutableStateOf(0.0) }
    var lastLongitude by remember { mutableStateOf(0.0) }

// LaunchedEffect는 cameraPositionState.position이 변경될 때마다 실행됩니다.
    val startTime = System.currentTimeMillis()


    LaunchedEffect(cameraPositionState.position) {
        val currentLatitude = cameraPositionState.position.target.latitude
        val currentLongitude = cameraPositionState.position.target.longitude

        // 현재 위치와 마지막 위치의 차이를 계산
        val latitudeChange = Math.abs(currentLatitude - lastLatitude)
        val longitudeChange = Math.abs(currentLongitude - lastLongitude)

        // 특정 임계값 이상으로 변했을 때만 실행
        if (latitudeChange > threshold || longitudeChange > threshold) {

            val toilets = readToiletInfoFromDB(
                context = context,
                latitude = currentLatitude,
                longitude = currentLongitude
            )
            // 마지막 위치 업데이트
            lastLatitude = currentLatitude
            lastLongitude = currentLongitude
            mapViewModel.updateMatchingToiletInfo(toilets)
            Log.d(TAG, "화장실 수 : ${mapUiState.matchingToiletInfo.size}")
        }
    }
    val endTime = System.currentTimeMillis()
    val elapsedTime = endTime - startTime

    Log.d(TAG, "첫번째 걸린시간: $elapsedTime")
    Log.d(TAG, "마커 시작")

    val second_startTime = System.currentTimeMillis()

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = mapProperties,
        uiSettings = mapUiSettings,
        onMyLocationButtonClick = onMyLocationButtonClick,
        cameraPositionState = cameraPositionState,
    ) {
        mapUiState.matchingToiletInfo.forEach { documentSnapshot ->
            val markerState = MarkerState(
                position = LatLng(
                    documentSnapshot.data?.get("latitude") as Double,
                    documentSnapshot.data?.get("longitude") as Double
                )
            )
            Marker(
                state = markerState,
                tag = documentSnapshot,
                onClick = { marker ->
                    mapViewModel.updateShowBottomSheet(show = true)
                    mapViewModel.updateClickedMarkerInfo(markerInfo = marker.tag as DocumentSnapshot?)
                    mapViewModel.updateClickedMarker(marker = markerState)
                    true
                },
                icon = if (mapUiState.clickedMarker?.position == markerState.position) {
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                } else {
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                }

            )
        }
    }
    val second_endTime = System.currentTimeMillis()
    val second_elapsedTime = second_endTime - second_startTime

    Log.d(TAG, "두번째 걸린시간: $second_elapsedTime")

    Log.d(TAG, "마커 끝")

    if (mapUiState.showBottomSheet) {
        MapBottomSheet(
            onSheetDismissed = {
                mapViewModel.updateShowBottomSheet(show = false)
            },
            markerInfo = mapUiState.clickedMarkerInfo
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBottomSheet(
    onSheetDismissed: () -> Unit,
    markerInfo: DocumentSnapshot?
) {
    val bottomSheetState: SheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onSheetDismissed() },
        sheetState = bottomSheetState,
    ) {
        ToiletDetails(markerInfo = markerInfo)
    }
}

@Preview(showBackground = true)
@Composable
fun MapBottomSheetPreview() {
    ToiletKoreaTheme {
//        ToiletDetails(markerInfo = tempMarkerInfo)
    }
}


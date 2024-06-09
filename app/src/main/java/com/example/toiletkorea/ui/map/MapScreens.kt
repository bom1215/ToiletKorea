package com.example.toiletkorea.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.toiletkorea.MainActivity
import com.example.toiletkorea.R
import com.example.toiletkorea.TAG
import com.example.toiletkorea.ToiletScreen
import com.example.toiletkorea.data.readToiletInfoFromDB
import com.example.toiletkorea.ui.theme.ToiletKoreaTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.firebase.firestore.DocumentSnapshot
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState


var tempMarkerInfo : DocumentSnapshot? = null

@Composable
fun GoogleMapScreen(
    mapViewModel: MapViewModel = viewModel()
) {
    val mapUiState by mapViewModel.uiState.collectAsState()

    val startLocation = LatLng(37.528643684, 127.126365737)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startLocation, 15f)
    }
    val southWest = LatLng(33.241587, 125.189360) // 예: 한국의 남서쪽 경계
    val northEast = LatLng(38.50212, 130.49490) // 예: 한국의 북동쪽 경계

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoomPreference = 21f,
                minZoomPreference = 5f,
                isMyLocationEnabled = true,
                latLngBoundsForCameraTarget = LatLngBounds(southWest,northEast)
            )
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                mapToolbarEnabled = false,
                myLocationButtonEnabled = true,
            )
        )
    }

    val context = LocalContext.current
    val onMyLocationButtonClick: (() -> Boolean) = {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
            val location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            location?.let {
                // 사용자의 현재 위치를 지도의 가운데로 이동
                val cameraPosition = CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 15f)
                cameraPositionState.move(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
            true
        } else {
            false
        }
    }
    val threshold = 0.5 // 예시로 임계값 설정 (5km)
    var lastLatitude by remember { mutableStateOf(0.0) }
    var lastLongitude by remember { mutableStateOf(0.0) }

//    var matchingToiletInfo by remember { mutableStateOf<List<DocumentSnapshot>>(emptyList()) }

// LaunchedEffect는 cameraPositionState.position이 변경될 때마다 실행됩니다.
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
            mapViewModel.updateMatchingToiletInfo(toilets)
            Log.d(TAG, "화장실 수 : ${mapUiState.matchingToiletInfo.size}")

            // 마지막 위치 업데이트
            lastLatitude = currentLatitude
            lastLongitude = currentLongitude
        }
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = mapProperties,
        uiSettings = mapUiSettings,
        onMyLocationButtonClick = onMyLocationButtonClick,
        cameraPositionState = cameraPositionState,
    ) {
        mapUiState.matchingToiletInfo.forEach { documentSnapshot ->
            Marker(
                state = MarkerState(
                    position = LatLng(
                        documentSnapshot.data?.get("latitude") as Double,
                        documentSnapshot.data?.get("longitude") as Double
                    )
                ),
                tag = documentSnapshot,
                onClick = {marker ->
                    mapViewModel.updateShowBottomSheet(show = true)
                    mapViewModel.updateClickedMarkerInfo(markerInfo = marker.tag as DocumentSnapshot?)
//                    clickedMarkerInfo = marker.tag as DocumentSnapshot?
                    true
                }
            )
        }
    }

    if (mapUiState.showBottomSheet){
        MapBottomSheet (
            onSheetDismissed = {
                mapViewModel.updateShowBottomSheet(show = false)},
            markerInfo = mapUiState.clickedMarkerInfo)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBottomSheet(
    onSheetDismissed : () -> Unit,
    markerInfo: DocumentSnapshot?) {
    val bottomSheetState: SheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onSheetDismissed() },
        sheetState = bottomSheetState,
    ) {
        ToiletDetails(markerInfo = markerInfo)
    }
}


@Composable
fun LocationPermissionRequest(
    activity: MainActivity,
    navController: NavController
    ) {
    var locationPermissionsGranted by remember { mutableStateOf(activity.areLocationPermissionsAlreadyGranted()) }
    Log.d(TAG, "locationPermissionsGranted :$locationPermissionsGranted")

    var shouldShowPermissionRationale by remember {
        mutableStateOf(
            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }
    Log.d(TAG, "shouldShowPermissionRationale : $shouldShowPermissionRationale")

    val shouldDirectUserToApplicationSettings by remember {
        mutableStateOf(false)
    }
    Log.d(TAG, "shouldDirectUserToApplicationSettings : $shouldDirectUserToApplicationSettings")

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    Log.d(TAG, "locationPermissions : $locationPermissions")


//            ###위치 정보 권한 요청

    val locationPermissionLauncher = rememberLauncherForActivityResult( //이 함수는 활동의 결과를 처리 때 사용. 즉, 사용자가 위치 권한 요청에 응답할 때 해당 결과 처리
        contract = ActivityResultContracts.RequestMultiplePermissions(), // 여러개 권한 요청
        onResult = { permissions ->    //활동결과가 나왔을 때 실행되는 콜백함수, 위치 권한 부여되었는지 여부 확인
            locationPermissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
                acc && isPermissionGranted  // acc는 true가 초기값, user가 permission true해주면 acc와 and연산자로 true로 합쳐짐.
            }
            Log.d(TAG, "permissions :$permissions.values")

            Log.d(TAG, "locationPermissionsGranted :$locationPermissionsGranted")


            if (!locationPermissionsGranted) { // 사용자가 거절했을 경우
//                shouldShowPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                shouldShowPermissionRationale = true


                Log.d(TAG, "shouldShowPermissionRationale : $shouldShowPermissionRationale")
            } //shouldShowRequestPermissionRationale 은 사용자가 이전에 권한을 거부했고, 권한을 요청하는 이유를 설명해야 하는지 여부를 나타내는 부울 값을 반환
        })

    val lifecycleOwner = LocalLifecycleOwner.current
    Log.d(TAG, "lifecycleOwner: $lifecycleOwner")

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            Log.d(TAG, "event: $event")
            if (event == Lifecycle.Event.ON_START &&
                !locationPermissionsGranted) {
                locationPermissionLauncher.launch(locationPermissions)
                Log.d(TAG, "위치권한 요청 작동됨")

            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    )
    if (shouldShowPermissionRationale) {
        ShowPermissionRationale()
    }
    else if(locationPermissionsGranted){
        Log.d(TAG, "다음 화면 전환")
        navController.navigate(ToiletScreen.LoginMain.name)
    }

}

@Composable
fun ShowPermissionRationale(
){
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Log.d(TAG, "showPermissionRationale 시작")

    Scaffold (
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState) },
    ) {Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(it),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(id = R.drawable.main_logo),
            contentDescription = null
        )
    }
        LaunchedEffect(Unit) {
            scope.launch {
                val userAction = snackbarHostState.showSnackbar(
                    message ="Please authorize location permissions",
                    actionLabel = "Approve",
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true
                )
            }
        }
    }


@Composable
fun GPSRequestPermission(activity: MainActivity) {
    var locationPermissionsGranted by remember { mutableStateOf(activity.areLocationPermissionsAlreadyGranted()) }
    Log.d(TAG, "locationPermissionsGranted :$locationPermissionsGranted")

    var shouldShowPermissionRationale by remember {
        mutableStateOf(
            ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
        )
    }
    Log.d(TAG, "shouldShowPermissionRationale : $shouldShowPermissionRationale")

    var shouldDirectUserToApplicationSettings by remember {
        mutableStateOf(false)
    }
    Log.d(TAG, "shouldDirectUserToApplicationSettings : $shouldDirectUserToApplicationSettings")

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    Log.d(TAG, "locationPermissions : $locationPermissions")


//            ###위치 정보 권한 요청

    val locationPermissionLauncher = rememberLauncherForActivityResult( //이 함수는 활동의 결과를 처리 때 사용. 즉, 사용자가 위치 권한 요청에 응답할 때 해당 결과 처리
        contract = ActivityResultContracts.RequestMultiplePermissions(), // 여러개 권한 요청
        onResult = { permissions ->    //활동결과가 나왔을 때 실행되는 콜백함수, 위치 권한 부여되었는지 여부 확인
            locationPermissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
                acc && isPermissionGranted  // acc는 true가 초기값, user가 permission true해주면 acc와 and연산자로 true로 합쳐짐.
            }
            Log.d(TAG, "permissions :$permissions.values")

            Log.d(TAG, "locationPermissionsGranted :$locationPermissionsGranted")


            if (!locationPermissionsGranted) { // 사용자가 거절했을 경우
                shouldShowPermissionRationale = true
//                    ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                Log.d(TAG, "shouldShowPermissionRationale : $shouldShowPermissionRationale")

            } //shouldShowRequestPermissionRationale 은 사용자가 이전에 권한을 거부했고, 권한을 요청하는 이유를 설명해야 하는지 여부를 나타내는 부울 값을 반환
        })

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START &&
                !locationPermissionsGranted &&
                !shouldShowPermissionRationale) {
                locationPermissionLauncher.launch(locationPermissions)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    )

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) },)
    {
        Column (modifier = Modifier.padding(it)) {
            if (shouldShowPermissionRationale) {
                LaunchedEffect(Unit) {
                    scope.launch {
                        Log.d(TAG, "snackbar 띄워짐. ")
                        val userAction = snackbarHostState.showSnackbar(
                            message ="Please authorize location permissions",
                            actionLabel = "Approve",
                            duration = SnackbarDuration.Indefinite,
                            withDismissAction = true
                        )
                        when (userAction) {
                            SnackbarResult.ActionPerformed -> {
                                shouldShowPermissionRationale = false
                                locationPermissionLauncher.launch(locationPermissions)
                            }
                            SnackbarResult.Dismissed -> {
                                shouldShowPermissionRationale = false
                            }
                        }
                    }
                }
            }
        }

    }}}

@Preview(showBackground = true)
@Composable
fun MapBottomSheetPreview(){
    ToiletKoreaTheme {
//        ToiletDetails(markerInfo = tempMarkerInfo)
    }
}


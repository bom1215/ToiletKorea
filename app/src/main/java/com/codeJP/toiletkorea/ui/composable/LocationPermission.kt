package com.codeJP.toiletkorea.ui.composable

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.codeJP.toiletkorea.MainActivity
import com.codeJP.toiletkorea.R
import com.codeJP.toiletkorea.TAG
import com.codeJP.toiletkorea.ToiletScreen
import kotlinx.coroutines.launch

@Composable
fun LocationPermissionRequest(
    activity: MainActivity,
    navController: NavController
) {
    var locationPermissionsGranted by remember { mutableStateOf(activity.areLocationPermissionsAlreadyGranted()) }
    Log.d(TAG, "locationPermissionsGranted :$locationPermissionsGranted")

    var shouldShowPermissionRationale by remember {
        mutableStateOf(
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
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

    val locationPermissionLauncher =
        rememberLauncherForActivityResult( //이 함수는 활동의 결과를 처리 때 사용. 즉, 사용자가 위치 권한 요청에 응답할 때 해당 결과 처리
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
                !locationPermissionsGranted
            ) {
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
    } else if (locationPermissionsGranted) {
        Log.d(TAG, "다음 화면 전환")
        navController.navigate(ToiletScreen.LoginMain.name)
    }

}

@Composable
fun ShowPermissionRationale(
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Log.d(TAG, "showPermissionRationale 시작")

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
    ) {
        Column(
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
                    message = "Please authorize location permissions",
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
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
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

        val locationPermissionLauncher =
            rememberLauncherForActivityResult( //이 함수는 활동의 결과를 처리 때 사용. 즉, 사용자가 위치 권한 요청에 응답할 때 해당 결과 처리
                contract = ActivityResultContracts.RequestMultiplePermissions(), // 여러개 권한 요청
                onResult = { permissions ->    //활동결과가 나왔을 때 실행되는 콜백함수, 위치 권한 부여되었는지 여부 확인
                    locationPermissionsGranted =
                        permissions.values.reduce { acc, isPermissionGranted ->
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
                    !shouldShowPermissionRationale
                ) {
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
        Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) })
        {
            Column(modifier = Modifier.padding(it)) {
                if (shouldShowPermissionRationale) {
                    LaunchedEffect(Unit) {
                        scope.launch {
                            Log.d(TAG, "snackbar 띄워짐. ")
                            val userAction = snackbarHostState.showSnackbar(
                                message = "Please authorize location permissions",
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

        }
    }
}
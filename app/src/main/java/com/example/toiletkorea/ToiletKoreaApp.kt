package com.example.toiletkorea

import SettingMainPage
import SignUpPage
import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.toiletkorea.ui.FirstScreen
import com.example.toiletkorea.ui.map.LocationPermissionRequest
import com.example.toiletkorea.ui.login.LoginMainPage
import com.example.toiletkorea.ui.login.LoginPage
import com.example.toiletkorea.ui.map.MapMainScreen
import com.example.toiletkorea.model.snackbar.SnackbarManager
import com.example.toiletkorea.ui.bars.BarsViewModel
import com.example.toiletkorea.ui.bars.Sidebar
import com.example.toiletkorea.ui.bars.ToiletTopBar
import com.example.toiletkorea.ui.login.ForgetPasswordPage
import com.example.toiletkorea.ui.login.LoginViewModel
import kotlinx.coroutines.CoroutineScope


enum class ToiletScreen(@StringRes val title: Int) {
    PermissionRequest(title = R.string.permission_request),
    First(title = R.string.first_page),
    LoginMain(title = R.string.login_main_page),
    Login(title = R.string.login_page),
    SignUp(title = R.string.signUp_page),
    ForgotPassword(title = R.string.forgot_password_page),
    Map(title = R.string.map_page),
    SettingMain(title = R.string.setting_main)
}


@Composable
fun ToiletKoreaApp (
    modifier: Modifier = Modifier,
//    viewModel : ToiletViewModel = viewModel(),
    navController : NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ToiletScreen.valueOf(
        backStackEntry?.destination?.route ?: ToiletScreen.LoginMain.name
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val snackbarHostState = remember { SnackbarHostState() }
    val appState = rememberAppState(snackbarHostState)
    Column(){
        if (currentScreen == ToiletScreen.Map || currentScreen ==ToiletScreen.SettingMain){
            ToiletTopBar(drawerState = drawerState)
        }
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Sidebar(
                    navController = navController
                )
            },
            gesturesEnabled = false
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.primary)
                        }
                    )
                },
//            topBar = {
//                if (currentScreen == ToiletScreen.Map || currentScreen ==ToiletScreen.SettingMain){
//                    ToiletTopBar(
//                        drawerState = drawerState
//                    )
//                }
//            }
            ) { innerPadding ->
                //    val uiState by viewModel.uiState.collectAsState()
                NavHost(
                    navController = navController,
                    startDestination = ToiletScreen.First.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding()
                ) {
                    composable(route = ToiletScreen.First.name) {
                        FirstScreen(
                            activity = LocalContext.current as MainActivity,
                            navController)
                    }
                    composable(route = ToiletScreen.LoginMain.name) {
                        LoginMainPage(navController = navController)
                    }
                    composable(route = ToiletScreen.Login.name) {
                        LoginPage (navController = navController)
                    }
                    composable(route = ToiletScreen.ForgotPassword.name){
                        ForgetPasswordPage(navController = navController)
                    }

                    composable(route = ToiletScreen.SignUp.name) {
                        SignUpPage(navController = navController)
                    }
                    composable(route = ToiletScreen.Map.name) {
                        MapMainScreen()
                    }
                    composable(route = ToiletScreen.SettingMain.name){
                        SettingMainPage(navController = navController)
                    }

                    composable(route = ToiletScreen.PermissionRequest.name) {
                        LocationPermissionRequest(
                            activity = LocalContext.current as MainActivity,
                            navController)
                    }


                }
            }
        }
    }

}
@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(snackbarHostState, navController, snackbarManager, resources, coroutineScope) {
        ToiletKoreaAppState(snackbarHostState, navController, snackbarManager, resources, coroutineScope)
    }
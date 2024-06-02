package com.example.toiletkorea

import SignUpPage
import android.content.res.Resources
import androidx.annotation.StringRes
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
import com.example.toiletkorea.ui.bars.SidebarInfo
import com.example.toiletkorea.ui.bars.ToiletTopBar
import kotlinx.coroutines.CoroutineScope


enum class ToiletScreen(@StringRes val title: Int) {
    PermissionRequest(title = 123),
    First(title = 111),
    LoginMain(title = R.string.login_main_page),
    Login(title = R.string.login_page),
    SignUp(title = R.string.signUp_page),
    Map(title = R.string.Map_page),
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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SidebarInfo(
                navController = navController
            )
        }
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.padding(8.dp),
                    snackbar = { snackbarData ->
                        Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                    }
                )
            },
            topBar = {
                if (currentScreen != ToiletScreen.First){
                    ToiletTopBar(
                        drawerState = drawerState
                    )
                }
            }
        ) { innerPadding ->
            //    val uiState by viewModel.uiState.collectAsState()
            NavHost(
                navController = navController,
                startDestination = ToiletScreen.First.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
//            .verticalScroll(rememberScrollState())
                    .padding()
            ) {
                composable(route = ToiletScreen.LoginMain.name) {
                    LoginMainPage(navController = navController)
                }
                composable(route = ToiletScreen.Login.name) {
                    LoginPage (onNextButtonClicked = { destination ->
                        when (destination) {
//                            ToiletScreen.Login.name -> navController.navigate(ToiletScreen.Map.name)
                            ToiletScreen.Map.name -> navController.navigate(ToiletScreen.Map.name)
                        }
                })
                }

                composable(route = ToiletScreen.SignUp.name) {
                    SignUpPage(onNextButtonClicked = { destination ->
                        when (destination) {
                            ToiletScreen.Login.name -> navController.navigate(ToiletScreen.Login.name)
//                            ToiletScreen.Map.name -> navController.navigate(ToiletScreen.Map.name)
                        }
                    }
                    )
                }
                composable(route = ToiletScreen.Map.name) {
                    MapMainScreen()
                }
                composable(route = ToiletScreen.PermissionRequest.name) {
                    LocationPermissionRequest(
                        activity = LocalContext.current as MainActivity,
                        navController)
                }
                composable(route = ToiletScreen.First.name) {
                    FirstScreen(
                        activity = LocalContext.current as MainActivity,
                        navController)
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
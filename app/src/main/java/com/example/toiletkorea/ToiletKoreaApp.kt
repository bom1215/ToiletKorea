package com.example.toiletkorea

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.toiletkorea.ui.map.SidebarInfo
import com.example.toiletkorea.ui.login.SignUpPage
//import com.example.toiletkorea.ui.Sidebar
import com.example.toiletkorea.ui.map.ToiletTopBar
import com.example.toiletkorea.ui.theme.ToiletKoreaTheme


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
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            SidebarInfo()
        }

    ) {

        Scaffold(
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
                    LoginMainPage(onNextButtonClicked = { destination ->
                        when (destination) {
                            ToiletScreen.Login.name -> navController.navigate(ToiletScreen.Login.name)
                            ToiletScreen.SignUp.name -> navController.navigate(ToiletScreen.SignUp.name)
                        }
                    })
                }
                composable(route = ToiletScreen.Login.name) {
                    LoginPage (onNextButtonClicked = { destination ->
                        when (destination) {
                            ToiletScreen.Login.name -> navController.navigate(ToiletScreen.Map.name)
                        }
                })
                }

                composable(route = ToiletScreen.SignUp.name) {
                    SignUpPage(onNextButtonClicked = { navController.navigate(ToiletScreen.Map.name)})
                }
                composable(route = ToiletScreen.Map.name) {
                    MapMainScreen(activity = LocalContext.current as MainActivity)
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
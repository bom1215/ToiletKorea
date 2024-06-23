package com.codeJP.toiletkorea

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codeJP.toiletkorea.model.snackbar.SnackbarManager
import com.codeJP.toiletkorea.ui.FirstScreen
import com.codeJP.toiletkorea.ui.bars.Sidebar
import com.codeJP.toiletkorea.ui.bars.ToiletTopBar
import com.codeJP.toiletkorea.ui.composable.LocationPermissionRequest
import com.codeJP.toiletkorea.ui.login.EmailSentPage
import com.codeJP.toiletkorea.ui.login.ForgetPasswordPage
import com.codeJP.toiletkorea.ui.login.LoginMainPage
import com.codeJP.toiletkorea.ui.login.LoginPage
import com.codeJP.toiletkorea.ui.map.MapMainScreen
import com.codeJP.toiletkorea.ui.settings.AboutPage
import com.codeJP.toiletkorea.ui.settings.NoticePage
import com.codeJP.toiletkorea.ui.settings.PrivacyPolicy
import com.codeJP.toiletkorea.ui.settings.TermsConditions
import kotlinx.coroutines.CoroutineScope


enum class ToiletScreen(@StringRes val title: Int) {
    PermissionRequest(title = R.string.permission_request),
    First(title = R.string.first_page),
    LoginMain(title = R.string.login_main_page),
    Login(title = R.string.login_page),
    SignUp(title = R.string.signUp_page),
    ForgotPassword(title = R.string.forgot_password_page),
    Map(title = R.string.map_page),
    SettingMain(title = R.string.setting_main),
    About(title = R.string.about_page),
    Privacy(title = R.string.privacy_policy),
    Terms(title = R.string.terms_conditions_page),
    EmailSent(title = R.string.email_sent),
    Notice(title = R.string.notices)
}


@Composable
fun ToiletKoreaApp(
    modifier: Modifier = Modifier,
//    viewModel : ToiletViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ToiletScreen.valueOf(
        backStackEntry?.destination?.route ?: ToiletScreen.LoginMain.name
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val snackbarHostState = remember { SnackbarHostState() }
    val appState = rememberAppState(snackbarHostState)
    Column() {
        ToiletTopBar(
            drawerState = drawerState,
            currentScreen = currentScreen,
            navController = navController
        )
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Sidebar(
                    navController = navController,
                    currentScreen = currentScreen
                )
            },
            gesturesEnabled = if (currentScreen == ToiletScreen.Map || currentScreen == ToiletScreen.SettingMain) true else false
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(
                                snackbarData,
                                contentColor = Color.Black,
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                    )
                },
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
                            navController
                        )
                    }
                    composable(route = ToiletScreen.LoginMain.name) {
                        LoginMainPage(navController = navController)
                    }
                    composable(route = ToiletScreen.Login.name) {
                        LoginPage(navController = navController)
                    }
                    composable(route = ToiletScreen.ForgotPassword.name) {
                        ForgetPasswordPage(navController = navController)
                    }

                    composable(route = ToiletScreen.SignUp.name) {
                        SignUpPage(navController = navController)
                    }
                    composable(route = ToiletScreen.Map.name) {
                        MapMainScreen()
                    }
                    composable(route = ToiletScreen.SettingMain.name) {
                        SettingMainPage(navController = navController)
                    }
                    composable(route = ToiletScreen.About.name) {
                        AboutPage(navController = navController)
                    }
                    composable(route = ToiletScreen.Privacy.name) {
                        PrivacyPolicy()
                    }
                    composable(route = ToiletScreen.Terms.name) {
                        TermsConditions()
                    }

                    composable(route = ToiletScreen.PermissionRequest.name) {
                        LocationPermissionRequest(
                            activity = LocalContext.current as MainActivity,
                            navController
                        )
                    }
                    composable(route = ToiletScreen.EmailSent.name) {
                        EmailSentPage(navController = navController)
                    }
                    composable(route = ToiletScreen.Notice.name) {
                        NoticePage()
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
        ToiletKoreaAppState(
            snackbarHostState,
            navController,
            snackbarManager,
            resources,
            coroutineScope
        )
    }
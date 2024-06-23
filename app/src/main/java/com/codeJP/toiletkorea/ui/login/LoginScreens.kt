package com.codeJP.toiletkorea.ui.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codeJP.toiletkorea.R
import com.codeJP.toiletkorea.TAG
import com.codeJP.toiletkorea.ToiletScreen
import com.codeJP.toiletkorea.ui.composable.EmailField
import com.codeJP.toiletkorea.ui.composable.ForgotPasswordButton
import com.codeJP.toiletkorea.ui.composable.HorizontalLineWithText
import com.codeJP.toiletkorea.ui.composable.LoginSignUpButton
import com.codeJP.toiletkorea.ui.composable.LoginSignUpInfo
import com.codeJP.toiletkorea.ui.composable.LoginSignUpTitle
import com.codeJP.toiletkorea.ui.composable.LoginTextButton
import com.codeJP.toiletkorea.ui.composable.PasswordField
import com.codeJP.toiletkorea.ui.composable.RecommendSignUpButton
import com.codeJP.toiletkorea.ui.composable.SignInGoogleButton
import com.codeJP.toiletkorea.ui.theme.LoginTheme
import com.codeJP.toiletkorea.ui.theme.ToiletKoreaTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LoginMainPage(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel: LoginViewModel = hiltViewModel()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            result.data?.let { viewModel.handleGoogleSignInResult(result.resultCode, it) }
            if (result.resultCode != Activity.RESULT_OK) {
                // 구글 로그인이 실패한 경우, 추가적인 처리를 수행할 수 있습니다.
                // 예를 들어 실패 메시지를 사용자에게 표시하거나 로그인 화면을 다시 표시할 수 있습니다.
                return@rememberLauncherForActivityResult
            }
            navController.navigate(ToiletScreen.Map.name)
        })


    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(id = R.drawable.main_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(110.dp)
        )
        Spacer(modifier = Modifier.size(30.dp))
        LoginSignUpTitle(text = R.string.login_main_title)
        Spacer(modifier = Modifier.size(30.dp))
        LoginSignUpInfo(text = R.string.login_main_subtitle)
        Spacer(modifier = Modifier.size(30.dp))
        LoginSignUpButton(
            text = R.string.login,
            modifier = Modifier,
            action = { navController.navigate(ToiletScreen.Login.name) },
            navController = navController
        )
        Spacer(modifier = Modifier.size(30.dp))
        LoginSignUpButton(
            text = R.string.sign_up,
            modifier = Modifier,
            action = { navController.navigate(ToiletScreen.SignUp.name) },
            navController = navController
        )
        Spacer(modifier = Modifier.size(30.dp))
        HorizontalLineWithText(text = "Or")

        Spacer(modifier = Modifier.size(30.dp))

        val coroutineScope = rememberCoroutineScope()

        SignInGoogleButton(onClick = {
            coroutineScope.launch {
                val signInIntentSender = viewModel.googleSignIn()
                Log.d(TAG, "signInIntentSender: $signInIntentSender")

                signInIntentSender?.let {
                    launcher.launch(
                        IntentSenderRequest.Builder(it).build()
                    )
                }
            }
        })
        Spacer(modifier = Modifier.size(50.dp))
        LoginTextButton(
            text = R.string.login_as_a_guest,
            modifier = Modifier,
            action = { viewModel.signInAnonymously(navController = navController) })
    }
}

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoginTheme(title = R.string.welcome_back, subtitle = R.string.login_to_continue)
        Spacer(modifier = Modifier.size(48.dp))
        EmailField(
            value = uiState.email,
            onNewValue = { newEmail -> viewModel.onEmailChange(newEmail) })
        Spacer(modifier = Modifier.size(27.dp))
        PasswordField(
            value = uiState.password,
            onNewValue = { newPassword -> viewModel.onPasswordChange(newPassword) })
        Spacer(modifier = Modifier.size(57.dp))
        LoginSignUpButton(text = R.string.login, modifier = Modifier, action = {
            viewModel.onSignInClick(
                { navController.navigate(ToiletScreen.Map.name) })
        }, navController = navController)
        Spacer(modifier = Modifier.size(7.dp))
        ForgotPasswordButton(action = { navController.navigate(ToiletScreen.ForgotPassword.name) })
        Spacer(modifier = Modifier.size(44.dp))
        RecommendSignUpButton(action = { navController.navigate(ToiletScreen.SignUp.name) })
    }
}


@Composable
fun ForgetPasswordPage(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoginTheme(title = R.string.welcome_back, subtitle = R.string.login_to_continue)
        Spacer(modifier = Modifier.size(48.dp))
        EmailField(
            value = uiState.email,
            onNewValue = { newEmail -> viewModel.onEmailChange(newEmail) })
        Spacer(modifier = Modifier.size(30.dp))
        LoginSignUpButton(
            text = R.string.send,
            modifier = Modifier,
            action = {
                viewModel.onForgotPasswordClick(onNextButtonClicked = {
                    navController.navigate(route = ToiletScreen.EmailSent.name)
                })
            },
            navController = navController
        )
//        Spacer(modifier = Modifier.size(30.dp))
//        LoginSignUpButton(text = R.string.home, modifier = Modifier, action= {navController.navigate(route = ToiletScreen.Login.name)})
    }
}

@Composable
fun EmailSentPage(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.secondary)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.email_sent),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
        )

        LaunchedEffect(Unit) {
            delay(5000)
            navController.navigate(ToiletScreen.Login.name)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun EmailSentPagePreview() {
    ToiletKoreaTheme {
//        EmailSentPage()
    }
}


//@Preview(showBackground = true)
//@Composable
//fun LoginMainPagePreview(){
//    ToiletKoreaTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ){
//            LoginMainPageForPreview(
//                navController = rememberNavController())}
//    }
//}


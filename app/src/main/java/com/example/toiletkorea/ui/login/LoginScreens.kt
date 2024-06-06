package com.example.toiletkorea.ui.login

import SignUpPage
import android.app.Activity
import android.content.IntentSender
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.ModifierLocalMap
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnitType.Companion.Sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toiletkorea.R
import com.example.toiletkorea.ToiletScreen
import com.example.toiletkorea.ui.theme.ToiletKoreaTheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.toiletkorea.TAG
import com.example.toiletkorea.ToiletKoreaHiltApp
import com.example.toiletkorea.model.service.AccountService
import com.example.toiletkorea.ui.composable.EmailField
import com.example.toiletkorea.ui.composable.ForgotPasswordButton
import com.example.toiletkorea.ui.composable.HorizontalLineWithText
import com.example.toiletkorea.ui.composable.LoginSignUpButton
import com.example.toiletkorea.ui.composable.LoginSignUpInfo
import com.example.toiletkorea.ui.composable.LoginSignUpTitle
import com.example.toiletkorea.ui.composable.LoginTextButton
import com.example.toiletkorea.ui.composable.PasswordField
import com.example.toiletkorea.ui.composable.RecommendSignUpButton
import com.example.toiletkorea.ui.theme.LoginTheme
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun LoginMainPage (
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel: LoginViewModel = hiltViewModel()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            result.data?.let { viewModel.handleGoogleSignInResult(result.resultCode, it) }
        })


    Column (
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
        LoginSignUpButton(text = R.string.login, modifier = Modifier, action= {navController.navigate(ToiletScreen.Login.name)})
        Spacer(modifier = Modifier.size(30.dp))
        LoginSignUpButton(text = R.string.sign_up, modifier = Modifier, action= {navController.navigate(ToiletScreen.Login.name)})
        Spacer(modifier = Modifier.size(30.dp))
        HorizontalLineWithText(text = "Or")

        Spacer(modifier = Modifier.size(30.dp))

        val coroutineScope = rememberCoroutineScope()

        SignInGoogleButton(onClick =
        { coroutineScope.launch {
            val signInIntentSender = viewModel.googleSignIn()
            Log.d(TAG, "signInIntentSender: $signInIntentSender", )
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch
                ).build()
            )
            navController.navigate(ToiletScreen.Map.name)
        } }
        )
        Spacer(modifier = Modifier.size(30.dp))
        LoginTextButton(text = R.string.login_as_a_guest, modifier = Modifier, action ={viewModel.signInAnonymously(navController = navController)} )
    }
}

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    onNextButtonClicked: (Any?) -> Unit,
    ) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoginTheme(title  = R.string.welcome_back, subtitle = R.string.login_to_continue)
        Spacer(modifier = Modifier.size(48.dp))
        EmailField(value = uiState.email, onNewValue = {newEmail ->  viewModel.onEmailChange(newEmail) })
        Spacer(modifier = Modifier.size(27.dp))
        PasswordField(value = uiState.password, onNewValue = { newPassword -> viewModel.onPasswordChange(newPassword) })
        Spacer(modifier = Modifier.size(57.dp))
        LoginSignUpButton(text = R.string.login, modifier = Modifier, action= {viewModel.onSignInClick(onNextButtonClicked)})
        Spacer(modifier = Modifier.size(7.dp))
        ForgotPasswordButton(action = {})
        Spacer(modifier = Modifier.size(44.dp))
        RecommendSignUpButton (action = {})
    }
    }

@Composable
fun SignInGoogleButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        enabled = true,
        modifier = Modifier.size(width = 128.dp, height = 64.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.google_login),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            tint = Color.Unspecified,
        )
    }
}

@Composable
fun ForgetPasswordPage(
    modifier: Modifier = Modifier,
    navController: NavController
    ){

    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoginTheme(title  = R.string.welcome_back, subtitle = R.string.login_to_continue)
        Spacer(modifier = Modifier.size(48.dp))
        EmailField(value = uiState.email, onNewValue = {newEmail ->  viewModel.onEmailChange(newEmail) })
        Spacer(modifier = Modifier.size(30.dp))
        LoginSignUpButton(text = R.string.send, modifier = Modifier, action= {viewModel.onForgotPasswordClick()})
        Spacer(modifier = Modifier.size(30.dp))
        LoginSignUpButton(text = R.string.home, modifier = Modifier, action= {navController.navigate(route = ToiletScreen.Login.name)})
    }
}







//@Preview(showBackground = true)
//@Composable
//fun SignInGoogleButtonPreview(){
//    ToiletKoreaTheme {
//        SignInGoogleButton(onClick = {})
//    }
//}

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


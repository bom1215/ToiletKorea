package com.codeJP.toiletkorea.ui.login

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.codeJP.toiletkorea.TAG
import com.codeJP.toiletkorea.ToiletScreen
import com.codeJP.toiletkorea.model.ext.isValidEmail
import com.codeJP.toiletkorea.model.service.AccountService
import com.codeJP.toiletkorea.model.snackbar.SnackbarManager
import com.codeJP.toiletkorea.ui.ToiletKoreaViewModel
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.codeJP.toiletkorea.R.string as AppText

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService
) : ToiletKoreaViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState
//    var uiState = mutableStateOf(LoginUiState())
//        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun alreadyLogIn(): Boolean {
        return accountService.hasUser
    }

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(password = newValue)
    }

    //    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
    fun onSignInClick(onNextButtonClicked: () -> Unit) {
        Log.d(TAG, "로그인 처리 시작")

        if (!email.isValidEmail()) {
            Log.d(TAG, "이메일이 잘못됨")

            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            Log.d(TAG, "패스워드 입력 안됨")

            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {
            accountService.authenticate(email, password)
            Log.d(TAG, "로그인 처리 완료")
            try {
                onNextButtonClicked()
            } catch (e: Exception) {
                Log.d(TAG, "에러 발생 $e")
            }
        }
    }

    fun onForgotPasswordClick(onNextButtonClicked: () -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            onNextButtonClicked()
            SnackbarManager.showMessage(AppText.recovery_email_sent)
        }
    }

    fun signInAnonymously(navController: NavController) {
        launchCatching(snackbar = false) {
            try {
                accountService.createAnonymousAccount()
            } catch (ex: FirebaseAuthException) {
                throw ex
            }
        }
        navController.navigate(ToiletScreen.Map.name)
    }


    // launcher를 사용하여 구글 로그인을 처리하는 메서드
    fun handleGoogleSignInResult(resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            viewModelScope.launch {
                val signInResult = accountService.googleSignInWithIntent(intent = data)
            }
        }
    }

    suspend fun googleSignIn(): IntentSender? {
        return accountService.googleSignIn()
    }
}



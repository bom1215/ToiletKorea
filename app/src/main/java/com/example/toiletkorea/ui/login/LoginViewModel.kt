package com.example.toiletkorea.ui.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.toiletkorea.TAG
import com.example.toiletkorea.ToiletScreen
import com.example.toiletkorea.model.service.AccountService
import com.example.toiletkorea.model.ext.isValidEmail
import com.example.toiletkorea.ui.ToiletKoreaViewModel
import com.example.toiletkorea.model.snackbar.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.toiletkorea.R.string as AppText

@HiltViewModel
class LoginViewModel @Inject constructor(
    private  val accountService: AccountService
) : ToiletKoreaViewModel() {

    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState
//    var uiState = mutableStateOf(LoginUiState())
//        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(password = newValue)
    }

//    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
    fun onSignInClick(onNextButtonClicked: (Any?) -> Unit) {
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
                onNextButtonClicked(ToiletScreen.Map.name)
            }catch (e: Exception){
                Log.d(TAG, "에러 발생 $e")
            }
//            openAndPopUp(ToiletScreen.Map.name, ToiletScreen.First.name)

        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        launchCatching {
            accountService.sendRecoveryEmail(email)
            SnackbarManager.showMessage(AppText.recovery_email_sent)
        }
    }
}



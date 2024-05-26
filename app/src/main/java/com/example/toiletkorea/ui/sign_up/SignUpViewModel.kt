/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.example.makeitso.screens.sign_up

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.toiletkorea.ToiletScreen
import com.example.toiletkorea.R.string as AppText
import com.example.toiletkorea.model.ext.isValidEmail
import com.example.toiletkorea.model.ext.isValidPassword
import com.example.toiletkorea.model.ext.passwordMatches
import com.example.toiletkorea.model.service.AccountService
import com.example.toiletkorea.model.snackbar.SnackbarManager
import com.example.toiletkorea.ui.ToiletKoreaViewModel
import com.example.toiletkorea.ui.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
//    logService: LogService
) : ToiletKoreaViewModel() {
    private val _uiState = mutableStateOf(SignUpUiState())
    val uiState: State<SignUpUiState> = _uiState

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

  fun onRepeatPasswordChange(newValue: String) {
      _uiState.value = _uiState.value.copy(repeatPassword = newValue)
  }

//  fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
fun onSignUpClick(onNextButtonClicked: (Any?) -> Unit) {

    if (!email.isValidEmail()) {
      SnackbarManager.showMessage(AppText.email_error)
      return
    }

    if (!password.isValidPassword()) {
      SnackbarManager.showMessage(AppText.password_error)
      return
    }

    if (!password.passwordMatches(uiState.value.repeatPassword)) {
      SnackbarManager.showMessage(AppText.password_match_error)
      return
    }

    launchCatching {
      accountService.linkAccount(email, password)
//      openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
          onNextButtonClicked(ToiletScreen.Login.name)

    }
  }
}
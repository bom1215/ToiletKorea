package com.example.toiletkorea.ui.settings

import SettingsUiState
import com.example.toiletkorea.model.service.AccountService
import com.example.toiletkorea.ui.ToiletKoreaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val accountService: AccountService,
) : ToiletKoreaViewModel() {
  val uiState = accountService.currentUser.map {
    SettingsUiState(isAnonymousAccount = it.isAnonymous,
      username = it.username,
      email = it.email) }

//  fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)
//
//  fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)

  fun onSignOutClick(restartApp: () -> Unit) {
    launchCatching {
      accountService.signOut()
      restartApp()
    }
  }

  fun onDeleteMyAccountClick(restartApp: () -> Unit) {
    launchCatching {
      accountService.deleteAccount()
      restartApp()
    }
  }
}

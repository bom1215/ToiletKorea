package com.example.toiletkorea.ui.bars

import SettingsUiState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.makeitso.screens.sign_up.SignUpUiState
import com.example.toiletkorea.model.service.AccountService
import com.example.toiletkorea.ui.ToiletKoreaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BarsViewModel @Inject constructor(
    private val accountService: AccountService
) : ToiletKoreaViewModel() {
    val uiState = accountService.currentUser.map {
        BarsUiState(isAnonymousAccount = it.isAnonymous, username = it.username) }

}
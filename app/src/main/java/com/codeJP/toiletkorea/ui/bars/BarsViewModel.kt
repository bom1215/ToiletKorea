package com.codeJP.toiletkorea.ui.bars

import com.codeJP.toiletkorea.model.service.AccountService
import com.codeJP.toiletkorea.ui.ToiletKoreaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BarsViewModel @Inject constructor(
    private val accountService: AccountService
) : ToiletKoreaViewModel() {
    val uiState = accountService.currentUser.map {
        BarsUiState(isAnonymousAccount = it.isAnonymous, username = it.username)
    }

}
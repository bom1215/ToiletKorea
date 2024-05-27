package com.example.toiletkorea.ui.bars

import android.util.Log
import com.example.toiletkorea.TAG
import com.example.toiletkorea.model.service.AccountService
import com.example.toiletkorea.ui.ToiletKoreaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BarsViewModel @Inject constructor(
    private val accountService: AccountService
) : ToiletKoreaViewModel() {

    fun onSignOutClick(){
        launchCatching {
            try {
                accountService.signOut()
                Log.d(TAG, "sign Out Complete")

            }catch (e : Exception){
                Log.d(TAG, "signOut Error", e)
            }
        }
    }
}
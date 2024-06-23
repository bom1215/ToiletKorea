package com.codeJP.toiletkorea.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeJP.toiletkorea.model.snackbar.SnackbarManager
import com.codeJP.toiletkorea.model.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class ToiletKoreaViewModel() : ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
//                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}
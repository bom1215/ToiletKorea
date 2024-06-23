package com.codeJP.toiletkorea.ui.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val errorMessage: String? = ""
)

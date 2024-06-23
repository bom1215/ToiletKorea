package com.codeJP.toiletkorea.model

data class User(
    val id: String = "",
    val isAnonymous: Boolean = true,
    val username: String? = "",
    val email: String? = ""
)

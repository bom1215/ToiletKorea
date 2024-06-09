package com.example.toiletkorea.ui.login

import com.google.android.gms.maps.model.LatLng

data class LoginUiState(
    val email : String = "",
    val password : String = "",
    val username : String = "",
    val errorMessage: String? = ""
)

package com.example.toiletkorea.ui.login

import com.google.android.gms.maps.model.LatLng

data class LoginUiState(
    val username : String = "",
    val Password : String = "",
    val EmailAddress : String = "",
    val CurrentPosition : LatLng? = null,
)

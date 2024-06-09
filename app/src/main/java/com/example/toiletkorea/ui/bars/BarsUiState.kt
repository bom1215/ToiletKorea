package com.example.toiletkorea.ui.bars

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState

data class BarsUiState(
    val isAnonymousAccount: Boolean = true,
    val username: String? = "",
)

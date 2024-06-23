package com.codeJP.toiletkorea.ui.composable

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun LoginSignUpTitle(@StringRes text: Int) {
    Text(
        text = stringResource(text), fontSize = 24.sp,
        style = MaterialTheme.typography.displayLarge
    )
}

@Composable
fun LoginSignUpInfo(@StringRes text: Int) {
    Text(
        text = stringResource(text), fontSize = 16.sp,
        style = MaterialTheme.typography.displaySmall
    )
}

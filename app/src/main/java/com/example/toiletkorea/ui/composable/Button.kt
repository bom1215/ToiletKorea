package com.example.toiletkorea.ui.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginSignUpButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier.size(width = 185.dp, height = 47.dp),
        colors =
        buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(27)
    ) {
        Text(text = stringResource(text), fontSize = 18.sp,
            style = MaterialTheme.typography.displayMedium)
    }
}


@Composable
fun LoginTextButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    TextButton(
        onClick = action,
        modifier = modifier)
    {
        Text(text = stringResource(text), fontSize = 16.sp,
            style = MaterialTheme.typography.displaySmall)
    }
}



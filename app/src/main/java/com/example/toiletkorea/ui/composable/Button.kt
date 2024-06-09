package com.example.toiletkorea.ui.composable

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toiletkorea.MainActivity
import com.example.toiletkorea.R
import com.example.toiletkorea.ui.bars.BarsViewModel
import com.example.toiletkorea.ui.theme.ToiletKoreaTheme
import com.google.android.play.integrity.internal.i
import java.net.Inet4Address
import java.net.URL

@Composable
fun LoginSignUpButton(@StringRes text: Int, modifier: Modifier = Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier.size(width = 185.dp, height = 47.dp),
        colors =
        buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        shape = MaterialTheme.shapes.small
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

@Composable
fun ForgotPasswordButton(modifier: Modifier = Modifier, action: () -> Unit) {
    TextButton(
        onClick = action,
        modifier = modifier)
    {
        Text(text = "Forgot password?",
            fontSize = 16.sp,
            color = Color.LightGray,
            style = MaterialTheme.typography.displaySmall)
    }

}

@Composable
fun RecommendSignUpButton(modifier: Modifier = Modifier, action: () -> Unit){
    TextButton(
        onClick = action,
        modifier = modifier)
    {
        Column {
            Text(text = stringResource(R.string.dont_have_an_account),
                fontSize = 16.sp,
                color = Color.Black,
                style = MaterialTheme.typography.displaySmall)
            Text(text = stringResource(id = R.string.sign_up),
                fontSize = 16.sp,
                color = Color.LightGray,
                style = MaterialTheme.typography.displaySmall,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.align(Alignment.CenterHorizontally))
        }

    }
}

@Composable
fun SidebarButton(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    action: () -> Unit)
{
    val interactionSource = remember {
        MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val containerColor = if (isPressed) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.primaryContainer


    Button(
        onClick = action,
        modifier = modifier
            .size(width = 233.dp, height = 43.dp),
        contentPadding = PaddingValues(start = 15.dp), // 버튼 내부의 기본 패딩을 없앰
        interactionSource = interactionSource,
        colors = buttonColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.secondary,
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start){
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(30.dp))
            Text(text = stringResource(text),
                fontSize = 15.sp,
                style = MaterialTheme.typography.displayMedium,)
        }
    }
}

@Composable
fun ShareButton(
    context: Context,
    @StringRes text: Int,
    @StringRes url: Int,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, stringResource(url))
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val interactionSource = remember {
        MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val containerColor = if (isPressed) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.primaryContainer

    Button(
        onClick = {
                    startActivity(context, shareIntent, null)
                  },
        modifier = modifier
            .size(width = 233.dp, height = 43.dp),
        contentPadding = PaddingValues(start = 15.dp), // 버튼 내부의 기본 패딩을 없앰
        interactionSource = interactionSource,
        colors = buttonColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.secondary,
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start){
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(30.dp))
            Text(text = stringResource(text),
                fontSize = 15.sp,
                style = MaterialTheme.typography.displayMedium,)
        }
    }
}


@Composable
fun OpenMapAPPButton(
    activity: MainActivity,
    address: String,
    modifier: Modifier = Modifier){
    Column(
        modifier = modifier.size(width = 299.dp, height = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalLine()
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = {activity.moveToAnotherMapAPP(address)},
            modifier = Modifier
                .size(width = 191.dp, height = 35.dp)
                .clip(shape = MaterialTheme.shapes.extraSmall),
            colors = buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(start = 15.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start){
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(45f)
                        .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                        .align(Alignment.CenterVertically),
                ){
                    Icon(painterResource(id = R.drawable.arrow),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(20.dp)
                            .rotate(135f))
                }
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Get directions",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyLarge,)
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        HorizontalLine()
    }
}
@Preview(showBackground = true)
@Composable
fun OpenMapAPPButtonPreview(){
    ToiletKoreaTheme {
//        OpenMapAPPButton(address = "")
    }

}
@Preview(showBackground = true)
@Composable
fun SidebarButtonPreview(){
    ToiletKoreaTheme {
        SidebarButton(
            text = R.string.main_page,
            icon = R.drawable.main_page,
            action = {} )
    }
}

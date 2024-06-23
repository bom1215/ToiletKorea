import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codeJP.toiletkorea.R
import com.codeJP.toiletkorea.ToiletScreen
import com.codeJP.toiletkorea.ui.composable.HorizontalLine
import com.codeJP.toiletkorea.ui.settings.SettingsViewModel
import com.codeJP.toiletkorea.ui.theme.ToiletKoreaTheme

@Composable
fun SettingMainPage(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState(initial = SettingsUiState(false))

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.onPrimary)
                .fillMaxWidth()
                .height(94.dp)
                .align(Alignment.TopCenter)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 47.dp),
    ) {
        Profile(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(50.dp))
        Settings(navController = navController)
        Spacer(modifier = Modifier.size(36.dp))
        AccountSettings(
            modifier = Modifier.padding(15.dp),
            restartApp = { navController.navigate(ToiletScreen.LoginMain.name) })
    }
}

@Composable
fun Profile(modifier: Modifier = Modifier) {
    val viewModel: SettingsViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState(initial = SettingsUiState(false))
//    val uiState by viewModel.uiState


    val displayName = if (uiState.isAnonymousAccount || uiState.username.isNullOrEmpty()) {
        "Guest"
    } else {
        uiState.username
    }
    val displayEmail = if (uiState.isAnonymousAccount || uiState.email.isNullOrEmpty()) {
        ""
    } else {
        uiState.email
    }
    Column(
        modifier = modifier.size(width = 250.dp, height = 150.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.drawable.main_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(72.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = displayName!!,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = displayEmail!!,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
fun Settings(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(176.dp)
    ) {
        HorizontalLine()
        Spacer(modifier.size(15.dp))
        SettingOption(
            modifier = Modifier.weight(1f),
            icon = R.drawable.language,
            text = R.string.language,
            action = {})
        SettingOption(
            modifier = Modifier.weight(1f),
            icon = R.drawable.notice,
            text = R.string.notices,
            action = { navController.navigate(ToiletScreen.Notice.name) })
        SettingOption(
            modifier = Modifier.weight(1f),
            icon = R.drawable.about,
            text = R.string.about,
            action = { navController.navigate(ToiletScreen.About.name) })
        Spacer(modifier.size(15.dp))
        HorizontalLine()
    }
}

@Composable
fun SettingOption(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes text: Int,
    action: () -> Unit
) {
    Row(
        modifier = modifier
//            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(shape = CircleShape)
                .size(38.4.dp)
                .background(MaterialTheme.colorScheme.onPrimary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(23.dp)
            )
        }
        Text(
            text = stringResource(id = text),
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        if (text == R.string.language) {
            OutlinedButton(
                onClick = {},
                modifier = Modifier,
                shape = RectangleShape,
            ) {
                Text(
                    text = "English",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.displaySmall
                )
            }
        } else {
            IconButton(onClick = action) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }

    }
}

@Composable
fun AccountSettings(modifier: Modifier = Modifier, restartApp: () -> Unit) {

    val viewModel: SettingsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState(initial = SettingsUiState(false))
    Column(
        modifier = modifier.width(139.dp),
    ) {
        if (uiState.isAnonymousAccount) {
            AccountSettingOption(
                icon = R.drawable.login,
                text = R.string.login,
                action = restartApp
            )
        } else {
            AccountSettingOption(
                icon = R.drawable.logout,
                text = R.string.log_out,
                action = { viewModel.onSignOutClick(restartApp) })
            Spacer(Modifier.size(20.dp))
            AccountSettingOption(
                icon = R.drawable.delete,
                text = R.string.delete_account,
                action = { viewModel.onDeleteMyAccountClick(restartApp) })

        }
    }
}

@Composable
fun AccountSettingOption(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes text: Int,
    action: () -> Unit
) {
    IconButton(
        onClick = action,
        modifier = modifier
            .fillMaxWidth()
            .height(29.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = stringResource(id = text), color = Color.Red)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingMainPagePreview() {
    ToiletKoreaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
        ) {
            SettingMainPage(navController = rememberNavController())
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingAccountOptionPreview() {
    ToiletKoreaTheme {
        AccountSettings(restartApp = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ToiletKoreaTheme() {
        Profile()
    }
}

@Preview(showBackground = true)
@Composable
fun SettingPreview() {
    ToiletKoreaTheme {
        Settings(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun SettingOptionPreview() {
    ToiletKoreaTheme {
        SettingOption(icon = R.drawable.language, text = R.string.language, action = {})
    }
}

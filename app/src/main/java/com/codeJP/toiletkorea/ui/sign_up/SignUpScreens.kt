import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codeJP.makeitso.screens.sign_up.SignUpViewModel
import com.codeJP.toiletkorea.R
import com.codeJP.toiletkorea.ToiletScreen
import com.codeJP.toiletkorea.ui.composable.EmailField
import com.codeJP.toiletkorea.ui.composable.GeneralTextField
import com.codeJP.toiletkorea.ui.composable.LoginSignUpButton
import com.codeJP.toiletkorea.ui.composable.PasswordField
import com.codeJP.toiletkorea.ui.composable.RepeatPasswordField
import com.codeJP.toiletkorea.ui.theme.LoginTheme
import com.codeJP.toiletkorea.ui.theme.ToiletKoreaTheme

@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoginTheme(title = R.string.sign_up, subtitle = R.string.sign_up_subtitle)
        Spacer(modifier = Modifier.size(30.dp))
        GeneralTextField(
            title = R.string.username,
            value = uiState.username,
            onNewValue = { newUsername -> viewModel.onUsernameChange(newUsername) })
        Spacer(modifier = Modifier.size(27.dp))
        EmailField(
            value = uiState.email,
            onNewValue = { newEmail -> viewModel.onEmailChange(newEmail) })
        Spacer(modifier = Modifier.size(27.dp))
        PasswordField(
            value = uiState.password,
            onNewValue = { newPassword -> viewModel.onPasswordChange(newPassword) })
        Spacer(modifier = Modifier.size(27.dp))
        RepeatPasswordField(
            value = uiState.repeatPassword,
            onNewValue = { newRepeatPassword -> viewModel.onRepeatPasswordChange(newRepeatPassword) })
        Spacer(modifier = Modifier.size(45.dp))
        LoginSignUpButton(text = R.string.sign_up, action = {
            viewModel.onSignUpClick({
                navController.navigate(ToiletScreen.Login.name)
            })
        }, navController = navController)
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpPagePreview() {
    ToiletKoreaTheme {
        SignUpPage(navController = rememberNavController())
    }
}
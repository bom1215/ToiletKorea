import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.makeitso.screens.sign_up.SignUpViewModel
import com.example.toiletkorea.R
import com.example.toiletkorea.ToiletScreen
import com.example.toiletkorea.ui.composable.EmailField
import com.example.toiletkorea.ui.composable.GeneralTextField
import com.example.toiletkorea.ui.composable.LoginSignUpButton
import com.example.toiletkorea.ui.composable.PasswordField
import com.example.toiletkorea.ui.composable.RepeatPasswordField
import com.example.toiletkorea.ui.theme.LoginTheme
import com.example.toiletkorea.ui.theme.ToiletKoreaTheme

@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
    ) {

    val uiState by viewModel.uiState

    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoginTheme(title  = R.string.sign_up, subtitle = R.string.sign_up_to_continue)
        Spacer(modifier = Modifier.size(30.dp))
        GeneralTextField(title = R.string.username, value = uiState.username, onNewValue = {newUsername ->  viewModel.onUsernameChange(newUsername) })
        Spacer(modifier = Modifier.size(27.dp))
        EmailField(value = uiState.email, onNewValue = {newEmail ->  viewModel.onEmailChange(newEmail) })
        Spacer(modifier = Modifier.size(27.dp))
        PasswordField(value = uiState.password, onNewValue = {newPassword ->  viewModel.onPasswordChange(newPassword) })
        Spacer(modifier = Modifier.size(27.dp))
        RepeatPasswordField(value = uiState.repeatPassword, onNewValue = {newRepeatPassword ->  viewModel.onRepeatPasswordChange(newRepeatPassword) })
        Spacer(modifier = Modifier.size(58.dp))
        LoginSignUpButton(text = R.string.sign_up, action = {viewModel.onSignUpClick({
            navController.navigate(ToiletScreen.Login.name)})})
    }
}



@Preview(showBackground = true)
@Composable
fun SignUpPagePreview() {
    ToiletKoreaTheme {
        SignUpPage(navController = rememberNavController())
    }
}
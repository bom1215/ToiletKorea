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
import com.example.makeitso.screens.sign_up.SignUpViewModel
import com.example.toiletkorea.R
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
    onNextButtonClicked: (Any?) -> Unit,
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
        GeneralTextField(title = R.string.username, value = uiState.email, onNewValue = {newUsername ->  viewModel.onUsernameChange(newUsername) })
        Spacer(modifier = Modifier.size(27.dp))
        EmailField(value = uiState.email, onNewValue = {newEmail ->  viewModel.onEmailChange(newEmail) })
        Spacer(modifier = Modifier.size(27.dp))
        PasswordField(value = uiState.password, onNewValue = {newPassword ->  viewModel.onPasswordChange(newPassword) })
        Spacer(modifier = Modifier.size(27.dp))
        RepeatPasswordField(value = uiState.repeatPassword, onNewValue = {newRepeatPassword ->  viewModel.onRepeatPasswordChange(newRepeatPassword) })
        Spacer(modifier = Modifier.size(58.dp))
        LoginSignUpButton(text = R.string.sign_up, action = {viewModel.onSignUpClick(onNextButtonClicked)})
    }

}


//package com.example.makeitso.screens.sign_up
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.makeitso.R.string as AppText
//import com.example.makeitso.common.composable.*
//import com.example.makeitso.common.ext.basicButton
//import com.example.makeitso.common.ext.fieldModifier
//import com.example.makeitso.theme.MakeItSoTheme
//
//@Composable
//fun SignUpScreen(
//  openAndPopUp: (String, String) -> Unit,
//  viewModel: SignUpViewModel = hiltViewModel()
//) {
//  val uiState by viewModel.uiState
//
//  SignUpScreenContent(
//    uiState = uiState,
//    onEmailChange = viewModel::onEmailChange,
//    onPasswordChange = viewModel::onPasswordChange,
//    onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
//    onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) }
//  )
//}
//
//@Composable
//fun SignUpScreenContent(
//  modifier: Modifier = Modifier,
//  uiState: SignUpUiState,
//  onEmailChange: (String) -> Unit,
//  onPasswordChange: (String) -> Unit,
//  onRepeatPasswordChange: (String) -> Unit,
//  onSignUpClick: () -> Unit
//) {
//  val fieldModifier = Modifier.fieldModifier()
//
//  BasicToolbar(AppText.create_account)
//
//  Column(
//    modifier = modifier
//      .fillMaxWidth()
//      .fillMaxHeight()
//      .verticalScroll(rememberScrollState()),
//    verticalArrangement = Arrangement.Center,
//    horizontalAlignment = Alignment.CenterHorizontally
//  ) {
//    EmailField(uiState.email, onEmailChange, fieldModifier)
//    PasswordField(uiState.password, onPasswordChange, fieldModifier)
//    RepeatPasswordField(uiState.repeatPassword, onRepeatPasswordChange, fieldModifier)
//
//    BasicButton(AppText.create_account, Modifier.basicButton()) {
//      onSignUpClick()
//    }
//  }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SignUpScreenPreview() {
//  val uiState = SignUpUiState(
//    email = "email@test.com"
//  )
//
//  MakeItSoTheme {
//    SignUpScreenContent(
//      uiState = uiState,
//      onEmailChange = { },
//      onPasswordChange = { },
//      onRepeatPasswordChange = { },
//      onSignUpClick = { }
//    )
//  }
//}
@Preview(showBackground = true)
@Composable
fun SignUpPagePreview() {
    ToiletKoreaTheme {
        SignUpPage(onNextButtonClicked = {})
    }
}
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.toiletkorea.ToiletScreen
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

        Text(text = "Sign Up",
            style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.size(30.dp))

        TextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email") },
            modifier = Modifier.padding(20.dp)
        )
        TextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") },
            modifier = Modifier.padding(20.dp)
        )
        TextField(
            value = uiState.repeatPassword,
            onValueChange = { viewModel.onRepeatPasswordChange(it) },
            label = { Text("Repeat Password") },
            maxLines = 1,
            modifier = Modifier.padding(20.dp)
        )
        Spacer(modifier = Modifier.size(30.dp))

        Button(onClick = {viewModel.onSignUpClick(onNextButtonClicked)},
            modifier = Modifier.size(width = 150.dp, height = 50.dp)
        ) {
            Text("Sign Up",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold)
        }
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
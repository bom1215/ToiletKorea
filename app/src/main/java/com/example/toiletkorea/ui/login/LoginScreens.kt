package com.example.toiletkorea.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toiletkorea.R
import com.example.toiletkorea.ToiletScreen
import com.example.toiletkorea.ui.theme.ToiletKoreaTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginMainPage (
    modifier: Modifier = Modifier,
    onNextButtonClicked : (Any?) -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(id = R.drawable.main_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.size(30.dp))
        Text(text = "Hello Welcome!",
            style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.size(30.dp))
        Text("Welcoming message for the pee-lovers",
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.size(30.dp))
        Button(onClick = {onNextButtonClicked(ToiletScreen.Login.name)},
            modifier = Modifier.size(width = 150.dp, height = 50.dp)
        ) {
            Text("Login",
                style = MaterialTheme.typography.displayMedium)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = {onNextButtonClicked(ToiletScreen.SignUp.name)},
            modifier = Modifier.size(width = 150.dp, height = 50.dp)
        ) {
            Text("Sign Up",
                style = MaterialTheme.typography.displayMedium)
        }
        Spacer(modifier = Modifier.size(30.dp))

        Text(text = "Or via social media",
            style = MaterialTheme.typography.labelSmall)
    }

}


@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    onNextButtonClicked: (Any?) -> Unit,

    ) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {Text(text = "Welcome Back",
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(30.dp))
        Text("Login to continue",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.size(30.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.size(30.dp))

        Button(onClick = {onNextButtonClicked(ToiletScreen.Login.name)},
            modifier = Modifier.size(width = 150.dp, height = 50.dp)
        ) {
            Text("Login",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold)
        }
    }

    }

@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    onNextButtonClicked: (Any?) -> Unit,

) {
    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var EmailAddress by remember { mutableStateOf("") }

        Text(text = "Sign Up",
            style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = Modifier.size(30.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.padding(20.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.padding(20.dp)
        )
        TextField(
            value = EmailAddress,
            onValueChange = { EmailAddress = it },
            label = { Text("Email Address") },
            maxLines = 2,
            modifier = Modifier.padding(20.dp)
        )
        Spacer(modifier = Modifier.size(30.dp))

        Button(onClick = {onNextButtonClicked(ToiletScreen.Map.name)},
            modifier = Modifier.size(width = 150.dp, height = 50.dp)
        ) {
            Text("Sign Up",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold)
        }

    }

}





@Preview(showBackground = true)
@Composable
fun LoginMainPagePreview(){
    ToiletKoreaTheme {
        LoginMainPage(
            onNextButtonClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    ToiletKoreaTheme {
        LoginPage {}
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPagePreview() {
    ToiletKoreaTheme {
        SignUpPage(onNextButtonClicked = {})
    }
}
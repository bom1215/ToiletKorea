package com.codeJP.toiletkorea.ui.settings

import SettingOption
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codeJP.toiletkorea.R
import com.codeJP.toiletkorea.ToiletScreen
import com.codeJP.toiletkorea.ui.theme.ToiletKoreaTheme


@Composable
fun AboutPage(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top
    )
    {
        Spacer(modifier.size(15.dp))
        SettingOption(modifier = Modifier,
            icon = R.drawable.privacy,
            text = R.string.privacy_policy_text,
            action = { navController.navigate(ToiletScreen.Privacy.name) })
        Spacer(modifier.size(15.dp))
        SettingOption(modifier = Modifier,
            icon = R.drawable.terms_conditions,
            text = R.string.terms_and_conditions_text,
            action = { navController.navigate(ToiletScreen.Terms.name) })
//        Spacer(modifier.size(15.dp))

    }

}

@Composable
fun TermsConditions(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(15.dp)
    ) {
        Text(text = "Terms and Conditions", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = modifier.size(20.dp))
        Text(
            text = stringResource(id = R.string.terms_conditions),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun PrivacyPolicy(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(15.dp)
    ) {
        Text(text = "Privacy Policy", style = MaterialTheme.typography.displayLarge)
        Spacer(modifier = modifier.size(20.dp))
        Text(
            text = stringResource(id = R.string.privacy_policy),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PrivacyPolicyPreview(){
//    ToiletKoreaTheme {
//        PrivacyPolicy()
//    }
//}

@Preview(showBackground = true)
@Composable
fun AboutPagePreview() {
    ToiletKoreaTheme {
        AboutPage(navController = rememberNavController())
    }
}
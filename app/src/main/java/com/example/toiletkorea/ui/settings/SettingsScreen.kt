import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toiletkorea.R
import com.example.toiletkorea.ui.theme.ToiletKoreaTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun SettingMainPage(modifier : Modifier = Modifier){
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .height(94.dp),)
    Column(
        modifier = modifier.fillMaxSize().padding(top = 47.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Profile(modifier = modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = modifier.size(50.dp))
        Settings()
        Spacer(modifier = modifier.size(50.dp))
        AccountSettings(modifier = modifier.padding(15.dp))
    }
}

@Composable
fun Profile(modifier : Modifier = Modifier){
    Column(
        modifier = modifier.size(width = 150.dp, height = 150.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painterResource(id = R.drawable.main_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(72.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "name",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = "g4012s@gmail.com",
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.displaySmall
        )
        }
    }

@Composable
fun Settings(modifier : Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(176.dp)
    ){
        SettingOption(modifier = Modifier.weight(1f), icon = R.drawable.language, text = R.string.language)
        SettingOption(modifier = Modifier.weight(1f), icon = R.drawable.notice, text = R.string.notices)
        SettingOption(modifier = Modifier.weight(1f), icon = R.drawable.about, text = R.string.about)
    }
}
@Composable
fun SettingOption(
    modifier : Modifier = Modifier,
    @DrawableRes icon : Int,
    @StringRes text: Int){
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(23.dp))
        Text(text = stringResource(id = text),
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp))
        if(text == R.string.language){
            OutlinedButton(onClick = { /*TODO*/ },
                modifier = Modifier,
                shape = RectangleShape,
            ){
                Text(text = "English",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.displaySmall)
            }
        }else{
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowForward, contentDescription = null)
            }
        }

    }
}

@Composable
fun AccountSettings(modifier: Modifier = Modifier){
    Column(modifier = modifier.size(width = 139.dp, height = 58.dp),
        verticalArrangement = Arrangement.SpaceBetween,) {
        AccountSettingOption(icon = R.drawable.logout, text = R.string.log_out )
        AccountSettingOption(icon = R.drawable.delete, text = R.string.delete_account )
    }
}

@Composable
fun AccountSettingOption(
    modifier : Modifier = Modifier,
    @DrawableRes icon : Int,
    @StringRes text: Int
){
    IconButton(onClick = { /*TODO*/ },
        modifier = modifier
            .fillMaxWidth()
            .height(29.dp)) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
            horizontalArrangement = Arrangement.Start){
            Icon(painter = painterResource(id = icon),
                contentDescription = null,)
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = stringResource(id = text))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingMainPagePreview(){
    ToiletKoreaTheme {
        SettingMainPage()
    }
}


@Preview(showBackground = true)
@Composable
fun SettingAccountOptionPreview(){
    ToiletKoreaTheme {
        AccountSettings()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    ToiletKoreaTheme(){
        Profile()
    }
}
@Preview(showBackground = true)
@Composable
fun SettingPreview(){
    ToiletKoreaTheme {
        Settings()
    }
}
@Preview(showBackground = true)
@Composable
fun SettingOptionPreview(){
    ToiletKoreaTheme {
        SettingOption(icon = R.drawable.language, text = R.string.language)
    }
}

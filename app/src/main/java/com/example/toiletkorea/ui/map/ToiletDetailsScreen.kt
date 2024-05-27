package com.example.toiletkorea.ui.map

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.toiletkorea.MainActivity
import com.example.toiletkorea.R
import com.example.toiletkorea.ui.theme.ToiletKoreaTheme
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch




@Composable
fun ToiletDetails (
    modifier: Modifier = Modifier,
    markerInfo: DocumentSnapshot?,
    activity: MainActivity = LocalContext.current as MainActivity
) {
    Column (
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Text(text = markerInfo?.data?.get("name").toString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(bottom = 10.dp))
        Text(text = markerInfo?.data?.get("new_address").toString(),
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.padding(bottom = 10.dp))
        Text(text = "Operation Hours:",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(bottom = 10.dp))
        Text(text = markerInfo?.data?.get("open_hours").toString(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(bottom = 10.dp))
        Text(text = "Details",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(bottom = 10.dp))
        Button(onClick = {activity.moveToAnotherMapAPP(markerInfo?.data?.get("new_address").toString())},
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {Text(text = "GetDirections")

        }
        Text(text = "Details",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(bottom = 10.dp))
//        Row {
//            Image(painter = painterResource(id = R.drawable.main_logo),
//                contentDescription = null,
//                Modifier.size(150.dp))
//            Image(painter = painterResource(id = R.drawable.main_logo),
//                contentDescription = null,
//                Modifier.size(150.dp))
//            Image(painter = painterResource(id = R.drawable.main_logo),
//                contentDescription = null,
//                Modifier.size(150.dp))
//        }
    }
}



@Preview(showBackground = true)
@Composable
fun SidebarPreview() {
//    Sidebar ()
}

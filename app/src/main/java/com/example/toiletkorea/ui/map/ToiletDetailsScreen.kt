package com.example.toiletkorea.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toiletkorea.ui.theme.ToiletKoreaTheme
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ToiletTopBar(
    drawerState: DrawerState,

    ) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary),
        title = {},
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun SidebarInfo(){
    ModalDrawerSheet {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "name")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Main Page")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Your Favorites")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Your Reviews")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Notices")
        } }
}

@Composable
fun ToiletDetails (
    modifier: Modifier = Modifier,
    markerInfo: DocumentSnapshot?
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
        Button(onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {Text(text = "GetDirections")

        }
//        Row {
//            Image(painter = painterResource(id = R.drawable.poopimage),
//                contentDescription = null,
//                Modifier.size(150.dp))
//            Image(painter = painterResource(id = R.drawable.poopimage),
//                contentDescription = null,
//                Modifier.size(150.dp))
//            Image(painter = painterResource(id = R.drawable.poopimage),
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

package com.example.toiletkorea.ui.bars

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.toiletkorea.R
import com.example.toiletkorea.ui.theme.ToiletKoreaTheme
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
fun SidebarInfo(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BarsViewModel = hiltViewModel()

){
    ModalDrawerSheet {

        TextButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(R.drawable.profile),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(30.dp))
            Text(text = "name",
                style = MaterialTheme.typography.displayMedium)
        }
        Spacer(modifier = Modifier.size(30.dp))
        TextButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(R.drawable.main_page),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(60.dp))
            Text(text = "Main Page",
                style = MaterialTheme.typography.labelSmall)
        }
        TextButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(R.drawable.notice),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(60.dp))
            Text(text = "Notices",
                style = MaterialTheme.typography.labelSmall)
        }
        TextButton(onClick = {
//            viewModel.onSignOutClick()
        }) {
            Icon(
                painter = painterResource(R.drawable.logout),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(60.dp))
            Text(text = "Log Out",
                style = MaterialTheme.typography.labelSmall)
        }
    }
}


@Preview
@Composable
fun SidebarInfo(){
    ToiletKoreaTheme {
        SidebarInfo(navController  = rememberNavController())
    }
}

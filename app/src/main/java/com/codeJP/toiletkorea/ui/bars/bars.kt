package com.codeJP.toiletkorea.ui.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codeJP.toiletkorea.R
import com.codeJP.toiletkorea.ToiletScreen
import com.codeJP.toiletkorea.ui.composable.ShareButton
import com.codeJP.toiletkorea.ui.composable.SidebarButton
import com.codeJP.toiletkorea.ui.theme.ToiletKoreaTheme
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ToiletTopBar(
    drawerState: DrawerState,
    currentScreen: ToiletScreen,
    navController: NavController
) {
    if (currentScreen == ToiletScreen.Map ||
        currentScreen == ToiletScreen.SettingMain ||
        currentScreen == ToiletScreen.Notice ||
        currentScreen == ToiletScreen.About
    ) {
        MapAndSettingTopBar(drawerState = drawerState)
    } else if (
        currentScreen == ToiletScreen.Login ||
        currentScreen == ToiletScreen.SignUp ||
        currentScreen == ToiletScreen.ForgotPassword
    ) {
        AccountTopBar(drawerState = drawerState, navController = navController)
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MapAndSettingTopBar(drawerState: DrawerState) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
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
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        actions =
        {
            Icon(
                painter = painterResource(id = R.drawable.main_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 5.dp),
                tint = Color.Unspecified
            )
        },
        scrollBehavior = scrollBehavior
    )
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AccountTopBar(drawerState: DrawerState, navController: NavController) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go Back"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun Sidebar(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentScreen: ToiletScreen
) {
    ModalDrawerSheet(
        modifier = modifier,
        drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
        drawerShape = RectangleShape
    ) {
        Spacer(modifier = Modifier.size(26.dp))
        SidebarProfile()
        Spacer(modifier = Modifier.size(17.dp))
        SidebarOption(
            onSettingsClick = { navController.navigate(ToiletScreen.SettingMain.name) },
            onMainPageClick = { navController.navigate(ToiletScreen.Map.name) },
            currentScreen = currentScreen
        )
    }
}

@Composable
fun SidebarProfile() {
    val viewModel: BarsViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState(initial = BarsUiState(false))

    val displayName = if (uiState.isAnonymousAccount || uiState.username.isNullOrEmpty()) {
        "Guest"
    } else {
        uiState.username
    }
    Row(
        modifier = Modifier
            .size(width = 164.dp, height = 43.dp)
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(43.dp)
                .clip(shape = MaterialTheme.shapes.small)
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.size(9.dp))
        Text(
            text = displayName!!,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displayMedium,
            color = Color.Black
        )
    }
}


@Composable
fun SidebarOption(
    modifier: Modifier = Modifier,
    onMainPageClick: () -> Unit,
    onSettingsClick: () -> Unit,
    currentScreen: ToiletScreen
) {
    Column(
        modifier = modifier.size(width = 233.dp, height = 170.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SidebarButton(
            text = R.string.main_page,
            icon = R.drawable.main_page,
            action = { if (currentScreen != ToiletScreen.Map) onMainPageClick() })
        SidebarButton(
            text = R.string.settings,
            icon = R.drawable.settings,
            action = { if (currentScreen != ToiletScreen.SettingMain) onSettingsClick() })
        ShareButton(
            text = R.string.share,
            icon = R.drawable.share,
            url = R.string.share_url,
            context = LocalContext.current
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SidebarPreview() {
    ToiletKoreaTheme {
//        Sidebar(navController  = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun SidebarProfilePreview() {
    ToiletKoreaTheme {
        SidebarProfile()
    }
}

@Preview(showBackground = true)
@Composable
fun SidebarOptionPreview() {
    ToiletKoreaTheme {
        SidebarOption(
            onMainPageClick = {},
            onSettingsClick = {},
            currentScreen = ToiletScreen.Map
        )
    }
}



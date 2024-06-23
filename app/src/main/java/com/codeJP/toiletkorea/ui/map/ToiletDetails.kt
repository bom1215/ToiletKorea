package com.codeJP.toiletkorea.ui.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codeJP.toiletkorea.MainActivity
import com.codeJP.toiletkorea.R
import com.codeJP.toiletkorea.ui.composable.OpenMapAPPButton
import com.google.firebase.firestore.DocumentSnapshot


@Composable
fun ToiletDetails(
    modifier: Modifier = Modifier,
    markerInfo: DocumentSnapshot?,
    activity: MainActivity = LocalContext.current as MainActivity,
    mapViewModel: MapViewModel = viewModel()
) {
    val name: String = markerInfo?.data?.get("name") as? String ?: "unknown"
    val address: String = markerInfo?.data?.get("new_address") as? String ?: "unknown"
    val operationHourInfo: String = markerInfo?.data?.get("open_hours") as? String ?: "unknown"
    val diaper: Boolean = markerInfo?.data?.get("diaper") == "Y"
    val men_disabled: Int? = markerInfo?.data?.get("men_number_of_disabled_toilet") as? Int
    val women_disabled: Int? = markerInfo?.data?.get("women_number_of_disabled_toilet") as? Int
    val disabled: Boolean =
        (men_disabled != null && men_disabled > 0) && (women_disabled != null && women_disabled > 0)
    val emergencyAlarm: Boolean = markerInfo?.data?.get("emergencyAlarm") == "Y"
    val entrance_CCTV: Boolean = markerInfo?.data?.get("entrance_CCTV") == "Y"

    val mapUiState by mapViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .height(450.dp)
            .padding(start = 50.dp, end = 31.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        ToiletNameAddress(name = name, address = address)
        if (mapUiState.showBottomSheet) {
            OperationHours(operationHourInfo = operationHourInfo)
            ToiletFacilities(
                diaper = diaper,
                disabled = disabled,
                emergencyAlarm = emergencyAlarm,
                entrance_CCTV = entrance_CCTV
            )
            OpenMapAPPButton(activity = activity, address = address)
            Spacer(modifier = Modifier.size(80.dp))
        }
    }
}

@Composable
fun ToiletNameAddress(
    name: String,
    address: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = address,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 15.sp,
        )
    }
}


@Composable
fun OperationHours(modifier: Modifier = Modifier, operationHourInfo: String) {
    val filteredOpHours: String = OperationHourManger().FilterOperationHours(operationHourInfo)
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Operation hours:",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.size(7.dp))
        Text(
            text = filteredOpHours,
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 15.sp,
        )
    }
}

@Composable
fun ToiletFacilities(
    modifier: Modifier = Modifier,
    diaper: Boolean,
    disabled: Boolean,
    emergencyAlarm: Boolean,
    entrance_CCTV: Boolean,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Details",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.size(11.dp))
        Row(
            modifier = Modifier.size(width = 300.dp, height = 40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bathroom),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
            Icon(
                painter = painterResource(id = if (disabled) R.drawable.disabled else R.drawable.disabled_off),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
            Icon(
                painter = painterResource(id = if (diaper) R.drawable.diaper else R.drawable.diaper_off),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
            Icon(
                painter = painterResource(id = if (entrance_CCTV) R.drawable.cctv else R.drawable.cctv_off),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
            Icon(
                painter = painterResource(id = if (emergencyAlarm) R.drawable.emergency else R.drawable.emergency_off),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.Unspecified
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ToiletDetailsPreview() {
    ToiletDetails(markerInfo = null)
}

@Preview(showBackground = true)
@Composable
fun ToiletFacilitiesPreview() {
    ToiletFacilities(
        diaper = true, disabled = false, emergencyAlarm = true, entrance_CCTV = false
    )
}
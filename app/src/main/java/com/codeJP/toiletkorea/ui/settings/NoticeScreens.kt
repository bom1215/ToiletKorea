package com.codeJP.toiletkorea.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.codeJP.toiletkorea.R
import com.codeJP.toiletkorea.ui.theme.ToiletKoreaTheme

@Composable
fun NoticePage(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_noticies),
            contentDescription = null
        )
    }
}


@Preview(showBackground = true)
@Composable
fun NoticePagePreview() {
    ToiletKoreaTheme {
        NoticePage()
    }
}
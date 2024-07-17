package com.ownlab.ownlab_client.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.subTextColor

@Composable
fun EmptyContentMessage() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp, start = 16.dp, end = 16.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(60.dp)
                .height(60.dp),
            painter = painterResource(id = R.drawable.emoji_speech_balloon_empty),
            contentDescription = "등록된 내역이 없습니다."
        )
        Text(
            modifier = Modifier.padding(top = 26.dp),
            text = "등록된 내역이 없습니다.",
            color = subTextColor,
            fontSize = 14.sp,
        )
    }
}
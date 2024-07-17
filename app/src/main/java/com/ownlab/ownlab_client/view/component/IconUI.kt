package com.ownlab.ownlab_client.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ownlab.ownlab_client.R

@Composable
fun CancleIcon(onClick: () -> Unit) {
    val image: Painter = painterResource(id = R.drawable.icon_cancle)
    Image(
        painter = image,
        contentDescription = "Cancel Icon",
        modifier = Modifier
            .width(30.dp)
            .height(30.dp)
            .clickable(onClick = onClick)
    )
}

@Composable
fun ArrowDownIcon() {
    val image: Painter = painterResource(id = R.drawable.icon_arrow_down)
    Image(
        painter = image,
        contentDescription = "Arrow Down Icon",
        modifier = Modifier
            .width(25.dp)
            .height(25.dp)
    )
}

@Composable
fun ArrowLeftIcon(){
    val image: Painter = painterResource(id = R.drawable.icon_arrow_left)
    Image(
        painter = image,
        contentDescription = "Arrow Left Icon",
        modifier = Modifier
            .width(30.dp)
            .height(30.dp)
    )
}
package com.ownlab.ownlab_client.view.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ownlab.ownlab_client.blue
import com.ownlab.ownlab_client.gray100
import com.ownlab.ownlab_client.gray600
import com.ownlab.ownlab_client.lineColor
import com.ownlab.ownlab_client.subTextColor

@Composable
fun HorizontalDivider(
    color: Color = lineColor,
    thickness: Dp = 1.dp,
    modifier: Modifier = Modifier
) {
    TabRowDefaults.Divider(
        color = color,
        thickness = thickness,
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .height(thickness)
            .fillMaxWidth()
    )
}
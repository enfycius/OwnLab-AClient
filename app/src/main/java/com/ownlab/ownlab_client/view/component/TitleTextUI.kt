package com.ownlab.ownlab_client.view.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextWithRedAsterisk(text: String) {
    val annotatedString = buildAnnotatedString {
        append(text)
        withStyle(style = SpanStyle(color = Color.Red)) {
            append(" *")
        }
    }

    Text(
        text = annotatedString,
        fontSize = 13.sp,
        color = Color.Black,
        modifier = Modifier.padding(top = 18.dp)
    )
}
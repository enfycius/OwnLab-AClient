package com.ownlab.ownlab_client.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.blue
import com.ownlab.ownlab_client.gray050
import com.ownlab.ownlab_client.gray100
import com.ownlab.ownlab_client.gray300

@Composable
fun TopActionToolbar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            //.height(60.dp)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_navigation_m),
            contentDescription = "icon plus",
            modifier = Modifier
                .size(40.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        TextField(
            value = "",
            onValueChange = { /* Handle value change */ },
            modifier = Modifier
                .weight(1f) // Takes remaining space in the row
                .height(46.dp), // Adjust height as needed
            placeholder = {
                Text(
                    text = "나에게 딱 맞는 알바는, 알바HR",
                    style = TextStyle(color = Color.LightGray),
                    fontSize = 12.sp
                )
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 12.sp
            ),
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = gray050,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Image(
            painter = painterResource(id = R.drawable.icon_push),
            contentDescription = "icon push",
            modifier = Modifier
                .size(40.dp)
                .padding(5.dp)
        )
    }
}
package com.ownlab.ownlab_client.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ownlab.ownlab_client.textPrimaryColor

@Composable
fun InputTextFieldUI(
    textState: MutableState<String>,
    placeholderText: String,
    unit: String,
) {
    OutlinedTextField(
        value = textState.value,
        onValueChange = { textState.value = it },
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .height(53.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            backgroundColor = Color.White,
        ),
        placeholder = { Text(placeholderText, fontSize = 13.sp, color = Color.LightGray) },
        trailingIcon = { Text(text = unit, fontSize = 13.sp, color = Color.LightGray) }
    )
}

@Composable
fun TallInputTextFieldUI(
    textState: MutableState<String>,
    placeholderText: String
) {
    OutlinedTextField(
        value = textState.value,
        onValueChange = { textState.value = it },
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(8.dp),
        colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            backgroundColor = Color.White,
        ),
        placeholder = { Text(placeholderText, fontSize = 16.sp, color = Color.LightGray) },
    )
}
// 이력서 관리 input field
val CommonHeight = 53.dp
@Composable
fun InputInformation(
    mainText: String,
    placeholderText: String,
    textState: MutableState<String>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(top = 8.dp)
            .background(Color.White),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = mainText,
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(100.dp)
                    .align(Alignment.CenterVertically)
            )
            TextField(
                value = textState.value,
                onValueChange = { newText ->
                    textState.value = newText
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp
                ),
                singleLine = true,
                placeholder = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = placeholderText,
                            color = Color.LightGray,
                            fontSize = 13.sp,
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(53.dp)
                    .padding(start = 10.dp)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.White),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun InformationText(
    mainTitle:String,
    mainText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(Color.White),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = mainTitle,
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(100.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = mainText,
                color = textPrimaryColor,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(100.dp)
                    .align(Alignment.CenterVertically)
            )

        }
    }
}
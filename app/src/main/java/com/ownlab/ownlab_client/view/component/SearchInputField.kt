package com.ownlab.ownlab_client.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.gray050
@Composable
fun SearchInputField(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .padding(end = 8.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { newText ->
                text = newText
            },
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
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(text) // 여기서 검색어를 저장하는 콜백 호출
                    text = ""
                }
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = androidx.compose.ui.text.input.ImeAction.Search
            )
        )

        Image(
            painter = painterResource(id = R.drawable.icon_search),
            contentDescription = "icon push",
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterEnd)
                .padding(8.dp)
                .clickable {
                    onSearch(text) // 여기서 검색어를 저장하는 콜백 호출
                    text = ""
                }
        )
    }
}

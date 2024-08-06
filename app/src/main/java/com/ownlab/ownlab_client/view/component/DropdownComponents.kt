package com.ownlab.ownlab_client.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LazyColumSelectComponent(
    placeholder: String,
    options: List<String>,
    selectedOption: MutableState<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val currentSelectedOption by remember { derivedStateOf { selectedOption.value.ifEmpty { placeholder } } }

    Box(
        modifier = modifier
            .padding(top = 12.dp, bottom = 8.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { expanded = !expanded }
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = currentSelectedOption,
                    fontSize = 13.sp,
                    color = if (selectedOption.value.isEmpty()) Color.LightGray else Color.Black,
                    modifier = Modifier.weight(0.5f)
                )
                ArrowDownIcon()
            }

            if (expanded) {
                LazyColumDropdownMenu(
                    options = options,
                    onOptionSelected = { option ->
                        selectedOption.value = option
                        expanded = false
                    }
                )
            }
        }
    }
}
@Composable
fun LeftLazyColumSelectComponent(
    placeholder: String,
    options: List<String>,
    selectedOption: MutableState<String>,
) {
    var expanded by remember { mutableStateOf(false) }
    val currentSelectedOption by remember { derivedStateOf { selectedOption.value.ifEmpty { placeholder } } }

    Box(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, end = 15.dp)
            .width(100.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { expanded = !expanded }
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = currentSelectedOption,
                    fontSize = 13.sp,
                    color = if (selectedOption.value.isEmpty()) Color.LightGray else Color.Black,
                    modifier = Modifier.weight(0.5f)
                )
                ArrowDownIcon()
            }

            if (expanded) {
                LazyColumDropdownMenu(
                    options = options,
                    onOptionSelected = { option ->
                        selectedOption.value = option
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun LazyColumDropdownMenu(options: List<String>, onOptionSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        options.forEach { option ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOptionSelected(option) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = option,
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }
        }
    }
}

package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.blue
import com.ownlab.ownlab_client.view.component.ArrowDownIcon
import com.ownlab.ownlab_client.view.component.CancleIcon
import com.ownlab.ownlab_client.view.component.SettingBtn
import com.ownlab.ownlab_client.view.component.TextWithRedAsterisk

class RegionalSettingScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val navController = findNavController()
                RegionalSettingBackgroundBlock(navController = navController)
            }
        }
    }
}

@Composable
fun RegionalSettingBackgroundBlock(
    color: Color = Color.Gray,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color, shape = RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(336.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
                    )
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "지역설정",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.weight(1f) // 왼쪽 요소가 화면의 1/2을 차지하도록 설정
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // 간격 추가
                        CancleIcon {
                            navController.navigateUp()
                        }
                    }
                    TextWithRedAsterisk(text = "지역")
                    RegionSelectComponent(text = "지역", options = listOf("서울", "부산", "인천", "강원", "대구", "대전", "경기도", "부산"))
                    TextWithRedAsterisk(text = "상세 지역")
                    RegionSelectComponent(text = "상세지역", options = listOf("강남구", "서초구", "송파구"))
                    SettingBtn(text = "설정 완료") {
                        navController.navigateUp()
                    }
                }
            }
        }
    }
}

@Composable
fun RegionSelectComponent(text: String, options: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(text) }

    Box(
        modifier = Modifier
            .padding(top = 15.dp)
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
                    text = selectedOption,
                    fontSize = 13.sp,
                    color = if (selectedOption == text) Color.LightGray else Color.Black,
                    modifier = Modifier.weight(0.5f)
                )
                ArrowDownIcon()
            }

            DropdownMenu(
                expanded = expanded,
                onOptionSelected = { option ->
                    selectedOption = option
                    expanded = false
                },
                options = options // DropdownMenu에 options 리스트 전달
            )
        }
    }
}

@Composable
fun DropdownMenu(expanded: Boolean, onOptionSelected: (String) -> Unit, options: List<String>) {
    if (expanded) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .border(1.dp, Color.LightGray)
                .padding(vertical = 8.dp)
        ) {
            LazyColumn {
                items(options) { option ->
                    Text(
                        text = option,
                        fontSize = 13.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { onOptionSelected(option) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegionalSettingScreenPreview() {
    RegionalSettingBackgroundBlock(navController = NavController(LocalContext.current))
}
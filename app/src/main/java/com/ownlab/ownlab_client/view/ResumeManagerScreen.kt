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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.view.component.ArrowDownIcon
import com.ownlab.ownlab_client.view.component.FiveToggleButtonUI
import com.ownlab.ownlab_client.view.component.HorizontalDivider
import com.ownlab.ownlab_client.view.component.InputTextFieldUI
import com.ownlab.ownlab_client.view.component.SettingBtn
import com.ownlab.ownlab_client.view.component.TallInputTextFieldUI
import com.ownlab.ownlab_client.view.component.TextWithRedAsterisk
import com.ownlab.ownlab_client.view.component.ToggleButtonUI

class ResumeManagerScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val navController = findNavController()
                ResumeManagerScreenMain(navController = navController)
            }
        }
    }
}

@Composable
fun ResumeImage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = Color.White)
            .padding(top = 15.dp, bottom = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        val image: Painter = painterResource(id = R.drawable.icon_resume_image_upload)
        Image(
            painter = image,
            contentDescription = "Resume Upload Image",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        )
    }
}
@Composable
fun InputInformation(mainText: String, placeholderText: String, onClick: () -> Unit) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clickable(onClick = onClick)
            .padding(top = 5.dp)
            .background(Color.White),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
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
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = placeholderText,
                        color = Color.Gray,
                        fontSize = 13.sp,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(start = 10.dp)
                    .border(
                        width = 0.5.dp,
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
fun LazyColumSelectComponent(text: String, options: List<String>) {
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

            if (expanded) {
                LazyColumDropdownMenu(
                    options = options,
                    onOptionSelected = { option ->
                        selectedOption = option
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
            .border(1.dp, Color.LightGray)
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

@Composable
fun FullWidthWithText(text:String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 18.dp)
            .border(
                width = 0.5.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(13.dp), // 패딩을 추가하여 Row 내부의 여유 공간을 만듭니다
            verticalAlignment = Alignment.CenterVertically, // 수직 정렬을 중앙으로 설정합니다
            horizontalArrangement = Arrangement.Center
        ) {
            // 이미지 컴포저블
            Image(
                painter = painterResource(id = R.drawable.icon_plus),
                contentDescription = "icon plus",
                modifier = Modifier
                    .size(20.dp)
            )
            // 텍스트 컴포저블
            Text(
                text = text,
                modifier = Modifier
                    .padding(start = 16.dp), // 이미지와 텍스트 사이의 간격을 설정합니다
                fontSize = 15.sp
            )
        }
    }
}


@Composable
fun ResumeManagerScreenMain(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item { ResumeImage() }
        item { MainTitle(title = "기본정보") }
        basicInformationSection()
        item { MainTitle(title = "학력사항") }
        educationSection()
        item { MainTitle(title = "경력사항") }
        careerSection()
        item { MainTitle(title = "희망근무조건") }
        desiredWorkConditionsSection()
        item { MainTitle(title = "자기소개서") }
        selfIntroductionSection()
        item { MainTitle(title = "이력서 공개 여부") }
        resumeVisibilitySection(navController)
    }
}

private fun LazyListScope.basicInformationSection() {
    item { InputInformation(mainText = "이름", placeholderText = "이름을 입력하세요", onClick = {}) }
    item { InputInformation(mainText = "휴대폰번호", placeholderText = "휴대폰번호를 입력하세요", onClick = {}) }
    item { InputInformation(mainText = "이메일", placeholderText = "이메일을 입력하세요", onClick = {}) }
    item { InputInformation(mainText = "생년월일", placeholderText = "생년월일을 입력하세요", onClick = {}) }
    item { InputInformation(mainText = "성별", placeholderText = "성별을 입력하세요", onClick = {}) }
    item { InputInformation(mainText = "주소", placeholderText = "주소를 입력하세요", onClick = {}) }
    item { TextWithRedAsterisk(text = "이력서 제목") }
    item { InputInformation(mainText = "이력서 제목", placeholderText = "이력서 제목을 입력하세요", onClick = {}) }
}

private fun LazyListScope.educationSection() {
    item { TextWithRedAsterisk(text = "최종학력") }
    item {
        LazyColumSelectComponent(
            text = "학교",
            options = listOf("고등학교 졸업", "대학교 재학", "대학교 졸업", "대학원")
        )
    }
    item {
        LazyColumSelectComponent(
            text = "상태",
            options = listOf("입학", "재학", "졸업")
        )
    }
}

private fun LazyListScope.careerSection() {
    item { TextWithRedAsterisk(text = "경력구분") }
    item {
        ToggleButtonUI(
            firstOption = "신입",
            secondOption = "경력"
        )
    }
    item { FullWidthWithText(text = "경력사항 추가") }
    item { HorizontalDivider() }
}

private fun LazyListScope.desiredWorkConditionsSection() {
    item { TextWithRedAsterisk(text = "희망근무기간") }
    item {
        LazyColumSelectComponent(
            text = "희망근무기간",
            options = listOf("무관", "1년", "2년", "3년")
        )
    }
    item { TextWithRedAsterisk(text = "업/직종") }
    item { FullWidthWithText(text = "추가하기") }
    item { TextWithRedAsterisk(text = "고용형태") }
    item {
        FiveToggleButtonUI(
            firstOption = "알바",
            secondOption = "정규직",
            thirdOption = "계약직",
            fourthOption = "파견직",
            fifthOption = "프리랜서"
        )
    }
    item { TextWithRedAsterisk(text = "희망급여") }
    item { InputTextFieldUI(placeholderText = "숫자", unit = "원") }
}

private fun LazyListScope.selfIntroductionSection() {
    item { TextWithRedAsterisk(text = "자기소개서") }
    item { TallInputTextFieldUI(placeholderText = "내용") }
}

private fun LazyListScope.resumeVisibilitySection(navController: NavController) {
    item {
        ToggleButtonUI(
            firstOption = "공개",
            secondOption = "비공개"
        )
    }
    item {
        SettingBtn("등록하기") {
            navController.navigateUp()
        }
    }
}

@Preview
@Composable
fun ResumeManagerScreenPreview() {
    ResumeManagerScreenMain(navController = NavController(LocalContext.current))
}
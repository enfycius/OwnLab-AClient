package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.fragment.app.Fragment
import androidx.compose.foundation.layout.*
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.ownlab.ownlab_client.gray100
import com.ownlab.ownlab_client.subTextColor
import com.ownlab.ownlab_client.textPrimaryColor

class MyPageScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val navController = findNavController()
                MyPageScreen(navController)
            }
        }
    }
}

@Composable
fun MyProfile() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(6.dp)
                    .width(90.dp)
                    .fillMaxHeight()
                    .background(gray100)
            )
            Box(
                modifier = Modifier
                    .padding(
                        top = 19.dp,
                        bottom = 19.dp,
                    )
            ) {
                Row {
                    Text(
                        text = "임지현",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "구직자회원",
                        fontSize = 11.sp,
                        color = subTextColor,
                        modifier = Modifier
                            .padding(
                                top = 5.dp,
                            )
                    )
                }
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .padding(
                                top = 15.dp
                            )

                    ) {
                        Image(
                            painter = painterResource(id = com.ownlab.ownlab_client.R.drawable.edit_profile),
                            contentDescription = "",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun myPageTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = textPrimaryColor,
        modifier = Modifier
            .padding(
                top = 19.dp,
                bottom = 16.dp,
            )
    )
}

@Composable
fun profileChoice(mainText: String, onCLick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .clickable(onClick = onCLick)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = mainText,
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = com.ownlab.ownlab_client.R.drawable.icon_arrow_right),
                contentDescription = "",
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    }
}

@Composable
fun HorizontalDivider(
    color: Color = Color.LightGray,
    thickness: Dp = 1.dp,
    modifier: Modifier = Modifier
) {
    Divider(
        color = color,
        thickness = thickness,
        modifier = modifier
            .padding(top = 20.dp, bottom = 9.dp)
            .height(thickness)
            .fillMaxWidth()
    )
}

@Composable
fun MyPageScreen(navController: androidx.navigation.NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        MyProfile()
        HorizontalDivider()
        myPageTitle(title = "My")
        profileChoice(mainText = "이력서 및 경력관리", onCLick = {})
        profileChoice(mainText = "나의 스펙", onCLick = {})
        profileChoice(mainText = "AI 역량분석", onCLick = {})
        profileChoice(mainText = "지원 면접현황", onCLick = {})
        profileChoice(mainText = "스크랩공고", onCLick = {})
        profileChoice(mainText = "평가 관리", onCLick = {})
        HorizontalDivider()
        myPageTitle(title = "고객센터")
        profileChoice(mainText = "공지사항", onCLick = {})
        profileChoice(mainText = "자주 묻는 질문", onCLick = {})
        profileChoice(mainText = "1:1 문의", onCLick = {})
    }
}


@Preview
@Composable
fun MyPagePreview() {
    MyPageScreen(navController = rememberNavController())
}


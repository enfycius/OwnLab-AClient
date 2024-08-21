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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.gray100
import com.ownlab.ownlab_client.subTextColor
import com.ownlab.ownlab_client.textPrimaryColor
import com.ownlab.ownlab_client.view.component.HorizontalDivider

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
fun MyProfile(name: String, userType: String, imagePainter: Painter? = null) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_fullsize),
                contentDescription = "icon plus",
                modifier = Modifier
                    .width(90.dp)
                    .fillMaxHeight()
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
                        text = name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = userType,
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
                    imagePainter?.let {
                        Box(
                            modifier = Modifier
                                .padding(
                                    top = 15.dp
                                )
                        ) {
                            Image(
                                painter = it,
                                contentDescription = "",
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainTitle(title: String) {
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
fun ProfileChoice(mainText: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .clickable(onClick = onClick)
            //.padding(start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = mainText,
                modifier = Modifier.weight(1f)
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
fun MyPageScreen(navController: NavController) {
    val profileImage = painterResource(id = R.drawable.edit_profile)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        MyProfile(name = "알바 HR", userType="기업 회원", imagePainter = profileImage)
        MainTitle(title = "My")
        ProfileChoice(mainText = "이력서 작성") {
            navController.navigate(R.id.action_myPageScreen_to_resumeManagerScreen)
        }
        ProfileChoice(mainText = "이력서 보기"){
            navController.navigate(R.id.action_myPageScreen_to_resumeListScreen)
        }
        HorizontalDivider()
        MainTitle(title = "고객센터")
        ProfileChoice(mainText = "공지사항"){

        }
        ProfileChoice(mainText = "자주 묻는 질문"){

        }
        ProfileChoice(mainText = "1:1 문의"){

        }
    }
}


@Preview
@Composable
fun MyPagePreview() {
    MyPageScreen(navController = rememberNavController())
}


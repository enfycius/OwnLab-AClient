package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ownlab.ownlab_client.models.Resume
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.view.component.EmptyContentMessage
import com.ownlab.ownlab_client.view.component.InformationText
import com.ownlab.ownlab_client.viewmodels.ResumeViewModel
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeListScreen: Fragment() {
    private val tokenViewModel: TokenViewModel by viewModels()
    private val resumeViewModel: ResumeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val navController = findNavController()
                ResumeListMainScreen(navController = navController, resumeViewModel = resumeViewModel, tokenViewModel = tokenViewModel)
            }
        }
    }
}

@Composable
fun ResumeListMainScreen(navController: NavController, resumeViewModel: ResumeViewModel, tokenViewModel: TokenViewModel) {
    val resumeListResponse by resumeViewModel.resumeListResponse.observeAsState()
    val token by tokenViewModel.token.observeAsState("")

    LaunchedEffect(token) {
        if (token.isNullOrEmpty()) {
            Log.d("ResumeListMainScreen", "Token is empty, not fetching resumes.")
        } else {
            Log.d("ResumeListMainScreen", "Fetching resumes with token: $token")
            resumeViewModel.fetchResumes(token!!)
        }
    }

    when (val response = resumeListResponse) {
        is ApiResponse.Success -> {
            val resumes = response.data ?: emptyList()
            ResumeListContent(resumes)
        }
        is ApiResponse.Failure -> {
            EmptyContentMessage()
        }
        else -> {
            Log.d("ResumeListMainScreen", "No response or pending.")
        }
    }
}

@Composable
fun ResumeListContent(resumes: List<Resume>) {
    if (resumes.isNotEmpty()) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                val firstResume = resumes.first()
                ResumeDetailContent(firstResume)
            }
        }
    } else {
        EmptyContentMessage()
    }
}
@Composable
fun ResumeDetailContent(resume: Resume) {
    Column() {
        Text(
            text = "이력서 제목: ${resume.resumeTitle}",
            style = MaterialTheme.typography.h5.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        MyProfile(name = resume.name, userType = "알바 HR", imagePainter = null)
        MainTitle(title = "기본정보")
        InformationText(mainTitle = "휴대폰번호", mainText = resume.tel)
        InformationText(mainTitle = "이메일", mainText = resume.email)
        InformationText(mainTitle = "생년월일", mainText = resume.birth)
        InformationText(mainTitle = "성별", mainText = resume.sex)
        InformationText(mainTitle = "주소", mainText = resume.address)
        MainTitle(title = "학력사항")
        InformationText(mainTitle = "최종학력", mainText = resume.school)
        InformationText(mainTitle = "상태", mainText = resume.schoolState)
        MainTitle(title = "경력사항")
        InformationText(mainTitle = "회사명", mainText = resume.companyCareer)
        InformationText(mainTitle = "담당업무", mainText = resume.partCareer)
        InformationText(mainTitle = "근무 했던 시간대", mainText = resume.workTime)
        InformationText(mainTitle = "근무 했던 시작일", mainText = resume.workStart)
        InformationText(mainTitle = "근무 종료일", mainText = resume.workEnd)
        InformationText(mainTitle = "현재 근무 여부", mainText = resume.working.toString())
        InformationText(mainTitle = "Work", mainText = resume.work)
        MainTitle(title = "희망 근무 조건")
        InformationText(mainTitle = "시도", mainText = resume.sido)
        InformationText(mainTitle = "시군구", mainText = resume.sigungu)
        InformationText(mainTitle = "업직종", mainText = resume.firstWork)
        InformationText(mainTitle = "업무", mainText = resume.secondWork)
        InformationText(mainTitle = "고용형태", mainText = resume.workType)
        InformationText(mainTitle = "희망 근무 기간", mainText = resume.wishWorkTerm)
        InformationText(mainTitle = "희망급여", mainText = resume.wishWorkTermEtc)
        InformationText(mainTitle = "희망급여 타입", mainText = resume.wishSalaryType)
        InformationText(mainTitle = "자기소개서", mainText = resume.ps)
        InformationText(mainTitle = "이력서 공개 여부", mainText = resume.openPermission.toString())
    }
}
package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.models.ResumeResponse
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.view.component.BorderedCenteredContentUI
import com.ownlab.ownlab_client.view.component.CenteredImageSelector
import com.ownlab.ownlab_client.view.component.CheckboxInput
import com.ownlab.ownlab_client.view.component.FiveToggleButtonUI
import com.ownlab.ownlab_client.view.component.HorizontalDivider
import com.ownlab.ownlab_client.view.component.InputInformation
import com.ownlab.ownlab_client.view.component.InputTextFieldUI
import com.ownlab.ownlab_client.view.component.LazyColumSelectComponent
import com.ownlab.ownlab_client.view.component.LeftLazyColumSelectComponent
import com.ownlab.ownlab_client.view.component.SettingBtn
import com.ownlab.ownlab_client.view.component.TallInputTextFieldUI
import com.ownlab.ownlab_client.view.component.TextWithRedAsterisk
import com.ownlab.ownlab_client.view.component.ToggleButtonStateUI
import com.ownlab.ownlab_client.view.component.ToggleButtonUI
import com.ownlab.ownlab_client.viewmodels.ResumeViewModel
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import com.ownlab.ownlab_client.viewmodels.interfaces.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ResumeManagerScreen : Fragment() {
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
                ResumeManagerScreenMain(
                    navController = navController,
                    tokenViewModel = tokenViewModel,
                    resumeViewModel = resumeViewModel
                )
            }
        }
    }
}

@Composable
fun ResumeManagerScreenMain(
    navController: NavController,
    tokenViewModel: TokenViewModel,
    resumeViewModel: ResumeViewModel
) {
    val token by tokenViewModel.token.observeAsState()
    val resumeResponse by resumeViewModel.resumeResponse.observeAsState()
    LaunchedEffect(token) {
        if (token == null) {
            Log.d("토큰없음", "하이")
        }
        else {
            Log.d("토큰 있음", token!!)
        }
    }
    LaunchedEffect(resumeResponse) {
        when (resumeResponse) {
            is ApiResponse.Success -> {
                Log.d("ResumeUploadSuccess", "Success: ${(resumeResponse as ApiResponse.Success<ResumeResponse>).data.statusCode}")
                try {
                    navController.navigate(R.id.ownlab_main_nav)
                } catch (e: IllegalArgumentException) {
                    Log.e("NavigationError", "Invalid argument for navigation", e)
                }
            }
            is ApiResponse.Failure -> {
                Log.d("ResumeUploadError", "Error: ${(resumeResponse as ApiResponse.Failure).code}")
                try {
                    val action = BoardRegisterFragmentDirections.boardRegister2ChkDialog("네트워크 연결을 확인해주세요.")
                    navController.navigate(action)
                } catch (e: IllegalArgumentException) { }
            }
            else -> {
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item{
                val profileFile = rememberSaveable { mutableStateOf<File?>(null) }
                LaunchedEffect(profileFile.value) { resumeViewModel.profileFile.value = profileFile.value }
                CenteredImageSelector(profileFile)
            }
            item { MainTitle(title = "기본정보") }
            item { BasicInformationSection(resumeViewModel) }
            item { MainTitle(title = "학력사항") }
            item{ EducationSection(resumeViewModel) }
            item { MainTitle(title = "경력사항") }
            item{ CareerSection(resumeViewModel, navController) }
            item { MainTitle(title = "희망근무조건") }
            item { DesiredWorkConditionsSection(resumeViewModel) }
            item { MainTitle(title = "자기소개서") }
            item { SelfIntroductionSection(resumeViewModel)}
            item { MainTitle(title = "이력서 공개 여부") }
            item{ ResumeVisibilitySection(navController) }
            item {
                SettingBtn("등록하기") {
                    token?.let {
                        resumeViewModel.addResume(it, object : CoroutinesErrorHandler {
                            override fun onError(message: String) {
                                Log.d("Test3", message)
                                try {
                                    val action = BoardRegisterFragmentDirections.boardRegister2ChkDialog("네트워크 연결을 확인해주세요.")
                                    navController.navigate(action)
                                } catch (e: IllegalArgumentException) { }
                            }
                        })
                    }
                }
            }
        }
    }
}
@Composable
fun BasicInformationSection(resumeViewModel: ResumeViewModel) {
    val name = rememberSaveable  { mutableStateOf("") }
    val tel = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val birth = rememberSaveable { mutableStateOf("") }
    val sex = rememberSaveable { mutableStateOf("") }
    val address = rememberSaveable { mutableStateOf("") }
    val resumeTitle = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(name.value) { resumeViewModel.name.value = name.value }
    LaunchedEffect(tel.value) { resumeViewModel.tel.value = tel.value }
    LaunchedEffect(email.value) { resumeViewModel.email.value = email.value }
    LaunchedEffect(birth.value) { resumeViewModel.birth.value = birth.value }
    LaunchedEffect(sex.value) { resumeViewModel.sex.value = sex.value }
    LaunchedEffect(address.value) { resumeViewModel.address.value = address.value }
    LaunchedEffect(resumeTitle.value) { resumeViewModel.resumeTitle.value = resumeTitle.value }

    Column {
        InputInformation(mainText = "이름", placeholderText = "이름을 입력하세요", textState = name, onClick = {})
        InputInformation(mainText = "휴대폰번호", placeholderText = "휴대폰번호를 입력하세요", textState = tel, onClick = {})
        InputInformation(mainText = "이메일", placeholderText = "이메일을 입력하세요", textState = email, onClick = {})
        InputInformation(mainText = "생년월일", placeholderText = "생년월일을 입력하세요", textState = birth, onClick = {})
//        InputInformation(mainText = "성별", placeholderText = "성별을 입력하세요", textState = sex, onClick = {})

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "성별",
                color = Color.Gray,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(110.dp)
                    .align(Alignment.CenterVertically)
            )
            LazyColumSelectComponent(
                placeholder = "성별",
                options = listOf("남자", "여자"),
                selectedOption = sex
            )
        }
        InputInformation(mainText = "주소", placeholderText = "주소를 입력하세요", textState = address, onClick = {})
        TextWithRedAsterisk(text = "이력서 제목")
        InputInformation(mainText = "이력서 제목", placeholderText = "이력서 제목을 입력하세요", textState = resumeTitle, onClick = {})
    }
}
@Composable
fun EducationSection(resumeViewModel: ResumeViewModel) {
    val school = rememberSaveable { mutableStateOf("") }
    val schoolState = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(school.value) { resumeViewModel.school.value = school.value }
    LaunchedEffect(schoolState.value) { resumeViewModel.schoolState.value = schoolState.value }
    Column {
        TextWithRedAsterisk(text = "최종학력")
        LazyColumSelectComponent(
            placeholder = "학교",
            options = listOf("고등학교 졸업", "대학교 재학", "대학교 졸업", "대학원"),
            selectedOption = school
        )
        LazyColumSelectComponent(
            placeholder = "상태",
            options = listOf("입학", "재학", "졸업"),
            selectedOption = schoolState
        )
    }
}

@Composable
fun CareerSection(resumeViewModel: ResumeViewModel, navController: NavController) {
    val companyCareer = rememberSaveable { mutableStateOf("") }
    val partCareer = rememberSaveable { mutableStateOf("") }
    val workTime = rememberSaveable { mutableStateOf("") }
    val workStart = rememberSaveable { mutableStateOf("") }
    val workEnd = rememberSaveable { mutableStateOf("") }
    val working = rememberSaveable { mutableStateOf(false) }
    val work = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(companyCareer.value) { resumeViewModel.companyCareer.value = companyCareer.value }
    LaunchedEffect(workTime.value) { resumeViewModel.workTime.value = workTime.value }
    LaunchedEffect(workStart.value) { resumeViewModel.workStart.value = workStart.value }
    LaunchedEffect(workEnd.value) { resumeViewModel.workEnd.value = workEnd.value }
    LaunchedEffect(working.value) { resumeViewModel.working.value = working.value }
    LaunchedEffect(work.value) { resumeViewModel.work.value = work.value }
    LaunchedEffect(partCareer.value) { resumeViewModel.partCareer.value = partCareer.value }

    TextWithRedAsterisk(text = "경력구분")
    ToggleButtonUI(
        firstOption = "신입",
        secondOption = "경력",
        onOptionSelected = { selectedOption ->
            companyCareer.value = selectedOption
        }
    )

    if (companyCareer.value == "경력") {
        InputInformation(mainText = "파트 경력", placeholderText = "파트 경력을 입력하세요", textState = partCareer, onClick = {})
        InputInformation(mainText = "근무 했던 시간대", placeholderText = "근무 시간을 입력하세요", textState = workTime, onClick = {})
        InputInformation(mainText = "근무 했던 시작일", placeholderText = "근무 했던 경력의 시작일을 입력하세요", textState = workStart, onClick = {})
        InputInformation(mainText = "근무 종료일", placeholderText = "근무 했던 경력의 종료일을 입력하세요", textState = workEnd, onClick = {})
        CheckboxInput(mainText = "현재 근무 중", checkedState = working)
    }
}

@Composable
fun DesiredWorkConditionsSection(resumeViewModel: ResumeViewModel) {
    val sido = rememberSaveable { mutableStateOf("") }
    val sigungu = rememberSaveable { mutableStateOf("") }
    val firstWork = rememberSaveable { mutableStateOf("") }// 직종
    val secondWork = rememberSaveable { mutableStateOf("") } //업무
    val wishWorkTerm = rememberSaveable { mutableStateOf("") } // 희망 근무 기간
    val wishWorkTermEtc = rememberSaveable { mutableStateOf("") } // 시급(숫자)
    val workType = remember { mutableStateOf("알바") } // 고용형태
    val wishSalaryType = rememberSaveable { mutableStateOf("") } //희망급여 타입(시급, 일급, 월급 등)

    LaunchedEffect(sido.value) { resumeViewModel.sido.value = sido.value }
    LaunchedEffect(sigungu.value) { resumeViewModel.sigungu.value = sigungu.value }
    LaunchedEffect(firstWork.value) { resumeViewModel.firstWork.value = firstWork.value }
    LaunchedEffect(secondWork.value) { resumeViewModel.secondWork.value = secondWork.value }
    LaunchedEffect(wishWorkTerm.value) { resumeViewModel.wishWorkTerm.value = wishWorkTerm.value }
    LaunchedEffect(wishWorkTermEtc.value) { resumeViewModel.wishWorkTermEtc.value = wishWorkTermEtc.value }
    LaunchedEffect(workType.value) { resumeViewModel.workType.value = workType.value }
    LaunchedEffect(wishSalaryType.value) { resumeViewModel.wishSalaryType.value = wishSalaryType.value }

    Column {
        TextWithRedAsterisk(text = "근무지")
        InputInformation(mainText = "시도", placeholderText = "시도를 입력하세요", textState = sido, onClick = {})
        InputInformation(mainText = "시군구", placeholderText = "시군구를 입력하세요", textState = sigungu, onClick = {})
        TextWithRedAsterisk(text = "희망근무기간")
        LazyColumSelectComponent(
            placeholder = "희망근무기간",
            options = listOf("무관", "1년", "2년", "3년"),
            selectedOption = wishWorkTerm
        )
        TextWithRedAsterisk(text = "업/직종")
        InputInformation(mainText = "직종", placeholderText = "원하는 직종 입력하세요", textState = firstWork, onClick = {})
        InputInformation(mainText = "업무", placeholderText = "원하는 업무를 입력하세요", textState = secondWork, onClick = {})
        TextWithRedAsterisk(text = "고용형태")
        FiveToggleButtonUI(
            firstOption = "알바",
            secondOption = "정규직",
            thirdOption = "계약직",
            fourthOption = "파견직",
            fifthOption = "프리랜서"
        ) { selectedOption ->
            workType.value = selectedOption
        }
        TextWithRedAsterisk(text = "희망급여")
        Row(modifier = Modifier.fillMaxWidth()) {
            LeftLazyColumSelectComponent(
                placeholder = "시급",
                options = listOf("시급", "일급", "월급", "건별", "주급", "연봉"),
                selectedOption = wishSalaryType
            )
            InputTextFieldUI(textState = wishWorkTermEtc, placeholderText = "숫자", unit = "원")
        }
    }
}

@Composable
fun SelfIntroductionSection(resumeViewModel: ResumeViewModel) {
    val ps = rememberSaveable { mutableStateOf("") }
    LaunchedEffect(ps.value) { resumeViewModel.ps.value = ps.value }

    Column {
        TextWithRedAsterisk(text = "자기소개서")
        TallInputTextFieldUI(
            textState = ps,
            placeholderText = "내용"
        )
    }
}

@Composable
fun ResumeVisibilitySection(navController: NavController) {
    val openPermission = rememberSaveable { mutableStateOf(false) }
    Column {
        ToggleButtonStateUI(
            firstOption = "공개",
            secondOption = "비공개",
            toggleState = openPermission
        )
    }
}
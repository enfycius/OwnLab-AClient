package com.ownlab.ownlab_client.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ownlab.ownlab_client.models.Resume
import com.ownlab.ownlab_client.models.ResumeResponse
import com.ownlab.ownlab_client.models.mapJsonArrayToResume
import com.ownlab.ownlab_client.repository.ResumeRepository
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.viewmodels.interfaces.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

data class ValidationResult(
    val isValid: Boolean,
    val message: String? = null
)
@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val resumeRepo: ResumeRepository,
) : BaseViewModel() {

    val name = MutableLiveData<String>()
    val tel = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val birth = MutableLiveData<String>()
    val sex = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val resumeTitle = MutableLiveData<String>()
    val school = MutableLiveData<String>()
    val schoolState = MutableLiveData<String>()
    val companyCareer = MutableLiveData<String>()
    val partCareer = MutableLiveData<String>()
    val workTime = MutableLiveData<String>()
    val workStart = MutableLiveData<String>()
    val workEnd = MutableLiveData<String>()
    val working = MutableLiveData<Boolean>()
    val work = MutableLiveData<String>()
    val sido = MutableLiveData<String>()
    val sigungu = MutableLiveData<String>()
    val firstWork = MutableLiveData<String>()
    val secondWork = MutableLiveData<String>()
    val workType = MutableLiveData<String>()
    val wishWorkTerm = MutableLiveData<String>()
    val wishWorkTermEtc = MutableLiveData<String>()
    val wishSalaryType = MutableLiveData<String>()
    val ps = MutableLiveData<String>()
    val openPermission = MutableLiveData<Boolean>()
    val profileFile = MutableLiveData<File?>()

    private val _resumeResponse = MutableLiveData<ApiResponse<ResumeResponse>>()
    val resumeResponse: LiveData<ApiResponse<ResumeResponse>> get() = _resumeResponse
    private val _resumeListResponse = MutableLiveData<ApiResponse<List<Resume>>>()
    val resumeListResponse: LiveData<ApiResponse<List<Resume>>> get() = _resumeListResponse

    fun fetchResumes(token: String) {
        viewModelScope.launch {
            resumeRepo.getResume(token).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        Log.d("ResumeViewModel", "API Response: $response")
                        try {
                            val resumeListResponseData = response.data
                            val resumes = mapJsonArrayToResume(resumeListResponseData.resume)
                            _resumeListResponse.value = ApiResponse.Success(resumes)
                        } catch (e: Exception) {
                            Log.e("ResumeViewModelError", "Mapping error: ${e.message}")
                            _resumeListResponse.value = ApiResponse.Failure("Mapping error", -1)
                        }
                    }
                    is ApiResponse.Failure -> {
                        Log.d("ResumeViewModelError", "API Response: $response")
                        _resumeListResponse.value = ApiResponse.Failure(response.errorMessage, response.code)
                    }
                }
            }
        }
    }
    private fun mapJsonArrayToResume(jsonString: String): Resume {
        return Gson().fromJson(jsonString, Resume::class.java)
    }

    fun addResume(token: String?, coroutinesErrorHandler: CoroutinesErrorHandler) {
        val validationResult = validateResumeData()

        if (!validationResult.isValid) {
            coroutinesErrorHandler.onError(validationResult.message ?: "Unknown validation error")
            return
        }

        if (profileFile.value == null) {
            coroutinesErrorHandler.onError("이력서 이미지는 필수 입력 사항입니다.")
            return
        }

        baseRequest(_resumeResponse, coroutinesErrorHandler) {
            val resume = Resume(
                name = name.value ?: "",
                tel.value ?: "",
                email.value ?: "",
                birth.value ?: "",
                sex.value ?: "",
                address.value ?: "",
                resumeTitle.value ?: "",
                school.value ?: "",
                schoolState.value ?: "",
                companyCareer.value ?: "",
                partCareer.value ?: "",
                workTime.value ?: "",
                workStart.value ?: "",
                workEnd.value ?: "",
                working.value ?: false,
                work.value ?: "",
                sido.value ?: "",
                sigungu.value ?: "",
                firstWork.value ?: "",
                secondWork.value ?: "",
                workType.value ?: "",
                wishWorkTerm.value ?: "",
                wishWorkTermEtc.value ?: "",
                wishSalaryType.value ?: "",
                ps.value ?: "",
                openPermission.value ?: false
            )

            Log.d("ResumeViewModel", "Resume Data: $resume")
            Log.d("ResumeViewModel", "Profile File: ${profileFile.value?.absolutePath}")
            val file = profileFile.value ?: File("")
            resumeRepo.addResume(token, resume, file)
        }
    }
    fun validateResumeData(): ValidationResult {
        val name = name.value
        val email = email.value
        val phone = tel.value
        val birth = birth.value
        val sex = sex.value
        val address = address.value
        val resumeTitle = resumeTitle.value
        val school = school.value
        val schoolState = schoolState.value
        val sido = sido.value
        val sigungu = sigungu.value
        val firstWork = firstWork.value
        val secondWork = secondWork.value
        val workType = workType.value
        val wishWorkTerm = wishWorkTerm.value
        val wishWorkTermEtc = wishWorkTermEtc.value
        val wishSalaryType = wishSalaryType.value
        val ps = ps.value

        return when {
            name.isNullOrBlank() -> ValidationResult(false, "이름은 필수 입력 사항입니다.")
            email.isNullOrBlank() -> ValidationResult(false, "이메일은 필수 입력 사항입니다.")
            phone.isNullOrBlank() -> ValidationResult(false, "전화번호는 필수 입력 사항입니다.")
            birth.isNullOrBlank() -> ValidationResult(false, "생년월일은 필수 입력 사항입니다.")
            sex.isNullOrBlank() -> ValidationResult(false, "성별은 필수 입력 사항입니다.")
            address.isNullOrBlank() -> ValidationResult(false, "주소는 필수 입력 사항입니다.")
            resumeTitle.isNullOrBlank() -> ValidationResult(false, "이력서 제목은 필수 입력 사항입니다.")
            school.isNullOrBlank() -> ValidationResult(false, "학교명은 필수 입력 사항입니다.")
            schoolState.isNullOrBlank() -> ValidationResult(false, "학교 상태는 필수 입력 사항입니다.")
            sido.isNullOrBlank() -> ValidationResult(false, "시도는 필수 입력 사항입니다.")
            sigungu.isNullOrBlank() -> ValidationResult(false, "시군구는 필수 입력 사항입니다.")
            firstWork.isNullOrBlank() -> ValidationResult(false, "원하는 직종은 필수 입력 사항입니다.")
            secondWork.isNullOrBlank() -> ValidationResult(false, "원하는 업무는 필수 입력 사항입니다.")
            workType.isNullOrBlank() -> ValidationResult(false, "고용형태는 필수 입력 사항입니다.")
            wishWorkTerm.isNullOrBlank() -> ValidationResult(false, "희망 근무 기간은 필수 입력 사항입니다.")
            wishWorkTermEtc.isNullOrBlank() -> ValidationResult(false, "희망 급여 필수 입력 사항입니다.")
            wishSalaryType.isNullOrBlank() -> ValidationResult(false, "희망 급여 유형은 필수 입력 사항입니다.")
            ps.isNullOrBlank() -> ValidationResult(false, "자기소개서는 필수 입력 사항입니다.")
            else -> ValidationResult(true)
        }
    }



    fun Resume.toRequestBodyMap(): Map<String, RequestBody> {
        return mapOf(
            "name" to name.toRequestBody(),
            "tel" to tel.toRequestBody(),
            "email" to email.toRequestBody(),
            "birth" to birth.toRequestBody(),
            "sex" to sex.toRequestBody(),
            "address" to address.toRequestBody(),
            "resumeTitle" to resumeTitle.toRequestBody(),
            "school" to school.toRequestBody(),
            "schoolState" to schoolState.toRequestBody(),
            "companyCareer" to companyCareer.toRequestBody(),
            "partCareer" to partCareer.toRequestBody(),
            "workTime" to workTime.toRequestBody(),
            "workStart" to workStart.toRequestBody(),
            "workEnd" to workEnd.toRequestBody(),
            "working" to working.toRequestBody(),
            "work" to work.toRequestBody(),
            "sido" to sido.toRequestBody(),
            "sigungu" to sigungu.toRequestBody(),
            "firstWork" to firstWork.toRequestBody(),
            "secondWork" to secondWork.toRequestBody(),
            "workType" to workType.toRequestBody(),
            "wishWorkTerm" to wishWorkTerm.toRequestBody(),
            "wishWorkTermEtc" to wishWorkTermEtc.toRequestBody(),
            "wishSalaryType" to wishSalaryType.toRequestBody(),
            "ps" to ps.toRequestBody(),
            "openPermission" to openPermission.toRequestBody()
        )
    }

    private fun String.toRequestBody(): RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), this)
    private fun Boolean.toRequestBody(): RequestBody = this.toString().toRequestBody()

}
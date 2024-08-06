package com.ownlab.ownlab_client.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ownlab.ownlab_client.models.Resume
import com.ownlab.ownlab_client.models.ResumeResponse
import com.ownlab.ownlab_client.repository.ResumeRepository
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.viewmodels.interfaces.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

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

    fun addResume(token: String?, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(_resumeResponse, coroutinesErrorHandler) {
        val resume = Resume(
            name.value ?: "",
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
         val file = profileFile.value?: throw IllegalArgumentException("Profile image is required")
        resumeRepo.addResume(token, resume, file)
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
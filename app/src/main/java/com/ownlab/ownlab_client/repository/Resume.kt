package com.ownlab.ownlab_client.repository

import com.ownlab.ownlab_client.models.Resume
import com.ownlab.ownlab_client.models.ResumeResponse
import com.ownlab.ownlab_client.service.ResumeApi
import com.ownlab.ownlab_client.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ResumeRepository @Inject constructor(private val resumeApi: ResumeApi) {
    fun addResume(token: String?, addResume: Resume, profileFile: File): Flow<ApiResponse<ResumeResponse>> = flow {
        val profileRequestBody = profileFile.asRequestBody("image/png".toMediaTypeOrNull())
        val profilePart = MultipartBody.Part.createFormData("profile", profileFile.name, profileRequestBody)

        fun Boolean.toRequestBody(): RequestBody = (if (this) 1 else 0).toString().toRequestBody("text/plain".toMediaTypeOrNull())
        try {
            val response = resumeApi.addResume(
                token = token!!,
                profile = profilePart,
                name = addResume.name.toRequestBody(),
                tel = addResume.tel.toRequestBody(),
                email = addResume.email.toRequestBody(),
                birth = addResume.birth.toRequestBody(),
                sex = addResume.sex.toRequestBody(),
                address = addResume.address.toRequestBody(),
                resumeTitle = addResume.resumeTitle.toRequestBody(),
                school = addResume.school.toRequestBody(),
                schoolState = addResume.schoolState.toRequestBody(),
                companyCareer = addResume.companyCareer.toRequestBody(),
                partCareer = addResume.partCareer.toRequestBody(),
                workTime = addResume.workTime.toRequestBody(),
                workStart = addResume.workStart.toRequestBody(),
                workEnd = addResume.workEnd.toRequestBody(),
                working = addResume.working.toRequestBody(),
                work = addResume.work.toRequestBody(),
                sido = addResume.sido.toRequestBody(),
                sigungu = addResume.sigungu.toRequestBody(),
                firstWork = addResume.firstWork.toRequestBody(),
                secondWork = addResume.secondWork.toRequestBody(),
                workType = addResume.workType.toRequestBody(),
                wishWorkTerm = addResume.wishWorkTerm.toRequestBody(),
                wishWorkTermEtc = addResume.wishWorkTermEtc.toRequestBody(),
                wishSalaryType = addResume.wishSalaryType.toRequestBody(),
                ps = addResume.ps.toRequestBody(),
                openPermission = addResume.openPermission.toRequestBody()
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(ApiResponse.Success(body))
                } else {
                    emit(ApiResponse.Failure("Response body is null", response.code()))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                emit(ApiResponse.Failure(errorBody, response.code()))
            }
        } catch (e: Exception) {
            emit(ApiResponse.Failure(e.message ?: "Unknown error", -1))
        }
    }
}
package com.ownlab.ownlab_client.service

import com.ownlab.ownlab_client.models.Resume
import com.ownlab.ownlab_client.models.ResumeListResponse
import com.ownlab.ownlab_client.models.ResumeResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ResumeApi {
    @Multipart
    @POST("resume/add_resume")
    suspend fun addResume(
        @Header("Authorization") token: String,
        @Part profile: MultipartBody.Part,
        @Part("name") name: RequestBody,
        @Part("tel") tel: RequestBody,
        @Part("email") email: RequestBody,
        @Part("birth") birth: RequestBody,
        @Part("sex") sex: RequestBody,
        @Part("address") address: RequestBody,
        @Part("resume_title") resumeTitle: RequestBody,
        @Part("school") school: RequestBody,
        @Part("school_state") schoolState: RequestBody,
        @Part("company_career") companyCareer: RequestBody,
        @Part("part_career") partCareer: RequestBody,
        @Part("work_time") workTime: RequestBody,
        @Part("work_start") workStart: RequestBody,
        @Part("work_end") workEnd: RequestBody,
        @Part("working") working: RequestBody,
        @Part("work") work: RequestBody,
        @Part("sido") sido: RequestBody,
        @Part("sigungu") sigungu: RequestBody,
        @Part("first_work") firstWork: RequestBody,
        @Part("second_work") secondWork: RequestBody,
        @Part("work_type") workType: RequestBody,
        @Part("wish_work_term") wishWorkTerm: RequestBody,
        @Part("wish_work_term_etc") wishWorkTermEtc: RequestBody,
        @Part("wish_salary_type") wishSalaryType: RequestBody,
        @Part("ps") ps: RequestBody,
        @Part("open_permission") openPermission: RequestBody
    ): Response<ResumeResponse>

    @POST("resume/get_resume")
    suspend fun getResume(
        @Header("Authorization") token: String
    ): Response<ResumeListResponse>
}
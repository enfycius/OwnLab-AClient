package com.ownlab.ownlab_client.service

import com.ownlab.ownlab_client.models.ApplicantInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApplicantManagementApi {
    @POST("applicant/get_applicant")
    suspend fun getApplicantInfo(@Header("Authorization") token: String): Response<ApplicantInfoResponse>
}
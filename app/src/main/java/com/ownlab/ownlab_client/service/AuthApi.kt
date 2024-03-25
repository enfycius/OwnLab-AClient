package com.ownlab.ownlab_client.service

import com.ownlab.ownlab_client.models.Auth
import com.ownlab.ownlab_client.models.Id
import com.ownlab.ownlab_client.models.IdChkResponse
import com.ownlab.ownlab_client.models.Info
import com.ownlab.ownlab_client.models.LoginResponse
import com.ownlab.ownlab_client.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(
        @Body auth: Auth,
    ): Response<LoginResponse>

    @POST("auth/email/check")
    suspend fun idChk(
        @Body emailChk: Id,
    ): Response<IdChkResponse>

    @POST("auth/register")
    suspend fun register(
        @Body info: Info,
    ): Response<RegisterResponse>

    // The below is NOT discussed yet
    @GET("auth/")
    suspend fun refreshToken(
        @Header("") token: String
    ): Response<LoginResponse>

    @POST("auth/company")
    suspend fun registerCompany(
        @Body info: Info
    ): Response<RegisterResponse>


}
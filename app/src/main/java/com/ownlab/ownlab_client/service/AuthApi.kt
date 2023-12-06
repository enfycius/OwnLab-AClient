package com.ownlab.ownlab_client.service

import com.ownlab.ownlab_client.models.Auth
import com.ownlab.ownlab_client.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(
        @Body auth: Auth,
    ): Response<LoginResponse>
}
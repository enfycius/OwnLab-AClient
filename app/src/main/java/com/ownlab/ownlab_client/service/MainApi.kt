package com.ownlab.ownlab_client.service

import com.ownlab.ownlab_client.models.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface MainApi {
    @GET("auth/email")
    suspend fun getUser(@Header("Authorization") token: String): Response<UserResponse>
}
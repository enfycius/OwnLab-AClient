package com.ownlab.ownlab_client.service

import com.ownlab.ownlab_client.models.ApplyPostRequest
import com.ownlab.ownlab_client.models.PostItemRequest
import com.ownlab.ownlab_client.models.PostItemResponse
import com.ownlab.ownlab_client.models.RadarResponse
import com.ownlab.ownlab_client.models.RegisterResponse
import com.ownlab.ownlab_client.models.SurveyItemResponse
import com.ownlab.ownlab_client.models.SurveyResultRequest
import com.ownlab.ownlab_client.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BoardApi {

    @GET("post/get_post")
    suspend fun getPostItems(@Header("Authorization") token: String): Response<PostItemResponse>

    @POST("post/add_post")
    suspend fun registerPostItems(@Header("Authorization") token: String, @Body postItemRequest: PostItemRequest): Response<RegisterResponse>

    @POST("post/apply_post")
    suspend fun applyPostItem(@Header("Authorization") token: String, @Body applyPostRequest: ApplyPostRequest): Response<RegisterResponse>
}
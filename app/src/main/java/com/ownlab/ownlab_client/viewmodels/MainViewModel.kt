package com.ownlab.ownlab_client.viewmodels

import androidx.lifecycle.MutableLiveData
import com.ownlab.ownlab_client.models.RadarResponse
import com.ownlab.ownlab_client.models.SurveyItemResponse
import com.ownlab.ownlab_client.models.SurveyResultRequest
import com.ownlab.ownlab_client.models.UserResponse
import com.ownlab.ownlab_client.repository.MainRepository
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.viewmodels.`interface`.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val mainRepo: MainRepository): BaseViewModel() {
    private val _userResponse = MutableLiveData<ApiResponse<UserResponse>>()
    private val _surveyQuestionsResponse = MutableLiveData<ApiResponse<SurveyItemResponse>>()
    private val _radarResponse = MutableLiveData<ApiResponse<RadarResponse>>()

    val userResponse = _userResponse
    val surveyQuestionsResponse = _surveyQuestionsResponse
    val radarResponse = _radarResponse

    fun getUser(token: String?, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(userResponse, coroutinesErrorHandler) {
        mainRepo.getUser(token)
    }

    fun getSurveyItems(token: String?, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(surveyQuestionsResponse, coroutinesErrorHandler) {
        mainRepo.getSurveyItems(token)
    }

    fun getModelResults(surveyResults: SurveyResultRequest, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(radarResponse, coroutinesErrorHandler) {
        mainRepo.getModelResults(surveyResults)
    }
}
package com.ownlab.ownlab_client.viewmodels

import androidx.lifecycle.MutableLiveData
import com.ownlab.ownlab_client.models.ErrorResponse
import com.ownlab.ownlab_client.models.SurveyItem
import com.ownlab.ownlab_client.models.SurveyItemResponse
import com.ownlab.ownlab_client.models.SurveyResult
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
    private val _errorResponse = MutableLiveData<ApiResponse<ErrorResponse>>()

    val userResponse = _userResponse
    val surveyQuestionsResponse = _surveyQuestionsResponse
    val errorResponse = _errorResponse

    fun getUser(token: String?, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(userResponse, coroutinesErrorHandler) {
        mainRepo.getUser(token)
    }

    fun getSurveyItems(token: String?, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(surveyQuestionsResponse, coroutinesErrorHandler) {
        mainRepo.getSurveyItems(token)
    }

    fun getModelResults(surveyResults: List<SurveyResult>, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(errorResponse, coroutinesErrorHandler) {
        mainRepo.getModelResults(surveyResults)
    }
}
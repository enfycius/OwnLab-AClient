package com.ownlab.ownlab_client.viewmodels

import androidx.lifecycle.MutableLiveData
import com.ownlab.ownlab_client.models.PostItemResponse
import com.ownlab.ownlab_client.models.RadarResponse
import com.ownlab.ownlab_client.models.SurveyItemResponse
import com.ownlab.ownlab_client.models.SurveyResultRequest
import com.ownlab.ownlab_client.models.UserResponse
import com.ownlab.ownlab_client.repository.BoardRepository
import com.ownlab.ownlab_client.repository.MainRepository
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.utils.SingleLiveData
import com.ownlab.ownlab_client.viewmodels.`interface`.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor (private val boardRepo: BoardRepository): BaseViewModel() {
    private val _postItemResponse = MutableLiveData<ApiResponse<PostItemResponse>>()

    val postItemResponse = _postItemResponse

    fun getPostItems(token: String?, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(postItemResponse, coroutinesErrorHandler) {
        boardRepo.getPostItems(token)
    }
}
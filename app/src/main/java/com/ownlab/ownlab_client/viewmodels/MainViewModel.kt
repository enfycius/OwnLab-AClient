package com.ownlab.ownlab_client.viewmodels

import androidx.lifecycle.MutableLiveData
import com.ownlab.ownlab_client.models.UserResponse
import com.ownlab.ownlab_client.repository.MainRepository
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.viewmodels.`interface`.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val mainRepo: MainRepository): BaseViewModel() {
    private val _userResponse = MutableLiveData<ApiResponse<UserResponse>>()
    val userResponse = _userResponse

    fun getUser(token: String?, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(userResponse, coroutinesErrorHandler) {
        mainRepo.getUser(token)
    }
}
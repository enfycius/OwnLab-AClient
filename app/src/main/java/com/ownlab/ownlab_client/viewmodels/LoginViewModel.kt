package com.ownlab.ownlab_client.viewmodels

import androidx.lifecycle.MutableLiveData
import com.ownlab.ownlab_client.models.Auth
import com.ownlab.ownlab_client.models.LoginResponse
import com.ownlab.ownlab_client.repository.AuthRepository
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.utils.SingleLiveData
import com.ownlab.ownlab_client.viewmodels.`interface`.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (private val authRepo: AuthRepository): BaseViewModel() {
    private var _loginResponse = SingleLiveData<ApiResponse<LoginResponse>>()
    val loginResponse = _loginResponse

    fun login(auth: Auth, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(loginResponse, coroutinesErrorHandler) {
        authRepo.login(auth)
    }
}
package com.ownlab.ownlab_client.viewmodels

import androidx.lifecycle.MutableLiveData
import com.ownlab.ownlab_client.models.Id
import com.ownlab.ownlab_client.models.IdChkResponse
import com.ownlab.ownlab_client.models.Info
import com.ownlab.ownlab_client.models.RegisterResponse
import com.ownlab.ownlab_client.repository.AuthRepository
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.viewmodels.`interface`.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor (private val authRepo: AuthRepository): BaseViewModel() {
    private var _idChkResponse = MutableLiveData<ApiResponse<IdChkResponse>>()
    private var _registerResponse = MutableLiveData<ApiResponse<RegisterResponse>>()

    val idChkResponse = _idChkResponse
    val registerResponse = _registerResponse

    fun idChk(id: Id, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(idChkResponse, coroutinesErrorHandler) {
        authRepo.idChk(id)
    }

    fun register(info: Info, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(registerResponse, coroutinesErrorHandler) {
        authRepo.register(info)
    }

    fun registerCompany(info: Info, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(registerResponse, coroutinesErrorHandler){
        authRepo.registerCompany(info)
    }
}
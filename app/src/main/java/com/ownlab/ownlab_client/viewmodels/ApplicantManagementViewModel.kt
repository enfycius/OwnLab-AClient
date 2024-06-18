package com.ownlab.ownlab_client.viewmodels

import androidx.lifecycle.MutableLiveData
import com.ownlab.ownlab_client.models.ApplicantInfo
import com.ownlab.ownlab_client.models.ApplicantInfoResponse
import com.ownlab.ownlab_client.models.ApplyPostRequest
import com.ownlab.ownlab_client.models.PostItemRequest
import com.ownlab.ownlab_client.models.PostItemResponse
import com.ownlab.ownlab_client.models.RegisterResponse
import com.ownlab.ownlab_client.repository.ApplicantManagementRepository
import com.ownlab.ownlab_client.repository.BoardRepository
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.viewmodels.interfaces.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApplicantManagementViewModel @Inject constructor (private val applicantManagementRepo: ApplicantManagementRepository): BaseViewModel() {
    private val _applicantInfoResponse = MutableLiveData<ApiResponse<ApplicantInfoResponse>>()

    val applicantInfoResponse = _applicantInfoResponse

    fun getApplicantInfo(token: String?, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(applicantInfoResponse, coroutinesErrorHandler) {
        applicantManagementRepo.getApplicantInfo(token)
    }
}
package com.ownlab.ownlab_client.repository

import com.ownlab.ownlab_client.models.ApplyPostRequest
import com.ownlab.ownlab_client.models.PostItemRequest
import com.ownlab.ownlab_client.service.ApplicantManagementApi
import com.ownlab.ownlab_client.service.BoardApi
import com.ownlab.ownlab_client.utils.Constants
import javax.inject.Inject

class ApplicantManagementRepository @Inject constructor (private val applicantManagementApi: ApplicantManagementApi) {
    private val constants: Constants = Constants()

    fun getApplicantInfo(token: String?) = constants.apiFlow {
        applicantManagementApi.getApplicantInfo(token!!)
    }
}
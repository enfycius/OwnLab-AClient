package com.ownlab.ownlab_client.repository

import com.ownlab.ownlab_client.service.MainApi
import com.ownlab.ownlab_client.utils.Constants
import javax.inject.Inject

class MainRepository @Inject constructor (private val mainApi: MainApi) {
    private val constants: Constants = Constants()
    fun getUser(token: String?) = constants.apiFlow {
        mainApi.getUser(token!!)
    }

    fun getSurveyItems(token: String?) = constants.apiFlow {
        mainApi.getSurveyItems(token!!)
    }
}
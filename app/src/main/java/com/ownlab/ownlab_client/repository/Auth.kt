package com.ownlab.ownlab_client.repository

import com.ownlab.ownlab_client.models.Auth
import com.ownlab.ownlab_client.models.Id
import com.ownlab.ownlab_client.models.Info
import com.ownlab.ownlab_client.service.AuthApi
import com.ownlab.ownlab_client.utils.Constants
import javax.inject.Inject

class AuthRepository @Inject constructor (private val authApi: AuthApi) {
    private val constants: Constants = Constants()

    fun login(auth: Auth) = constants.apiFlow {
        authApi.login(auth)
    }

    fun idChk(id: Id) = constants.apiFlow {
        authApi.idChk(id)
    }

    fun register(info: Info) = constants.apiFlow {
        authApi.register(info)
    }
}
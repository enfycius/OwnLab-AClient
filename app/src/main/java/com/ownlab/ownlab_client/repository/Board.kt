package com.ownlab.ownlab_client.repository

import com.ownlab.ownlab_client.service.BoardApi
import com.ownlab.ownlab_client.utils.Constants
import javax.inject.Inject

class BoardRepository @Inject constructor (private val boardApi: BoardApi) {
    private val constants: Constants = Constants()

    fun getPostItems(token: String?) = constants.apiFlow {
        boardApi.getPostItems(token!!)
    }
}
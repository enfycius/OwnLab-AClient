package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message")
    val message: String
)

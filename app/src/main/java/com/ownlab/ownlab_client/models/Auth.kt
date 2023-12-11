package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class Auth(
    @SerializedName("email")
    val email: String,
    @SerializedName("pwd")
    val password: String
)

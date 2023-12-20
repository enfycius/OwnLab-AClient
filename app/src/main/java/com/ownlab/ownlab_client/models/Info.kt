package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("email")
    val email: String,
    @SerializedName("pwd")
    val password: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("tel")
    val tel: String,
)

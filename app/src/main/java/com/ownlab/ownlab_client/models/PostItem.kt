package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class PostItem(
    @SerializedName("post_id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("email") val email: String
)
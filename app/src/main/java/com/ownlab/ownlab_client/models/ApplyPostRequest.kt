package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class ApplyPostRequest(
    @SerializedName("post_id") val id: Int,
    @SerializedName("assignee") val assignee: String,
)
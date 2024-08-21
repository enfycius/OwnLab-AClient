package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class ResumeListResponse(
    @SerializedName("resume") val resume: List<Any>
)
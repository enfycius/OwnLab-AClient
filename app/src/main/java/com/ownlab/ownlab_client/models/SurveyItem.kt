package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class SurveyItem(
    @SerializedName("id") val id: Int,
    @SerializedName("question") val question: String
)
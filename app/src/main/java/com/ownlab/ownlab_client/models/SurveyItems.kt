package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class SurveyItems(
    @SerializedName("question") val questions: List<String>
)
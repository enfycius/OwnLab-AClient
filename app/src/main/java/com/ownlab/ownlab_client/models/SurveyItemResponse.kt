package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class SurveyItemResponse(
    @SerializedName("survey_items") val surveyItems: List<SurveyItem>
)

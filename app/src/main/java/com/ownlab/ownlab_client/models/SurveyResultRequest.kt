package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class SurveyResultRequest(
    @SerializedName("survey_results") val surveyResults: List<SurveyResult>,
    @SerializedName("work_time") val workTime: Int
)

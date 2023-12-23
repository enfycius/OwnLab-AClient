package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class SurveyResult(
    @SerializedName("isChecked") var isChecked: Boolean,
    @SerializedName("id") var id: Int
)

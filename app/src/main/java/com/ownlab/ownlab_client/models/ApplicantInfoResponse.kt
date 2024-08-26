package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class ApplicantInfoResponse(
    @SerializedName("applicant_info") val applicantInfo: List<ApplicantInfo>
)

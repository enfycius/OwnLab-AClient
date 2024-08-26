package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class ApplicantInfo(
    @SerializedName("assignee") val assignee: String,
    @SerializedName("post_id") val id: Int,
    @SerializedName("registration_date") val registrationDate: String,
    @SerializedName("applicant") val applicant: String,
    @SerializedName("email") val email: String,
    @SerializedName("tel") val tel: String
)
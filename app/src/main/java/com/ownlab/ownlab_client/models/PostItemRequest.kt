package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class PostItemRequest(
    @SerializedName("title") val title: String,
    @SerializedName("contacts") val contacts: String,
    @SerializedName("assignee") val assignee: String,
    @SerializedName("registration_method") val registrationMethod: String,
    @SerializedName("address") val address: String,
    @SerializedName("detailed_link") val detailedLink: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String,
)
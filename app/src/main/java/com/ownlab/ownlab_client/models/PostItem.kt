package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class PostItem(
    @SerializedName("post_id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("contacts") val contacts: String,
    @SerializedName("email") val email: String,
    @SerializedName("assignee") val assignee: String,
    @SerializedName("registration_method") val registration_method: String,
    @SerializedName("address") val address: String,
    @SerializedName("detailed_link") val detailed_link: String,
    @SerializedName("start_date") val start_date: String,
    @SerializedName("end_date") val end_date: String,
    @SerializedName("registration_date") val registration_date: String
)
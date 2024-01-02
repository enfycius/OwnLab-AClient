package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class PostItemResponse(
    @SerializedName("post_items") val postItems: List<PostItem>
)

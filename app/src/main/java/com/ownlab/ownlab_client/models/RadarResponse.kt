package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class RadarResponse(
    @SerializedName("passion_ch") val passion: Double,
    @SerializedName("cooperation_ch") val cooperation: Double,
    @SerializedName("diligence_ch") val diligence: Double,
    @SerializedName("responsibility_ch") val responsibility: Double,
    @SerializedName("conductivity_ch") val conductivity: Double,
    @SerializedName("leadership_ch") val leadership: Double
)

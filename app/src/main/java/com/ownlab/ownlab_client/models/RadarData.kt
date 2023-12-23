package com.ownlab.ownlab_client.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RadarData(
    val type: RadarType,
    val value: Int
): Parcelable

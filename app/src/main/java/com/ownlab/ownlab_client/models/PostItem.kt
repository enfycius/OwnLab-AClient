package com.ownlab.ownlab_client.models

import android.os.Parcel
import android.os.Parcelable
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
    @SerializedName("registration_date") val registration_date: String,
    @SerializedName("count") val count: Int,
    @SerializedName("limitation") val limitation: Int,
    var isBookmarked: Boolean = false // 북마크 상태 추가
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte() // isBookmarked 읽기
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(contacts)
        parcel.writeString(email)
        parcel.writeString(assignee)
        parcel.writeString(registration_method)
        parcel.writeString(address)
        parcel.writeString(detailed_link)
        parcel.writeString(start_date)
        parcel.writeString(end_date)
        parcel.writeString(registration_date)
        parcel.writeInt(count)
        parcel.writeInt(limitation)
        parcel.writeByte(if (isBookmarked) 1 else 0) // isBookmarked 쓰기
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostItem> {
        override fun createFromParcel(parcel: Parcel): PostItem {
            return PostItem(parcel)
        }

        override fun newArray(size: Int): Array<PostItem?> {
            return arrayOfNulls(size)
        }
    }
}
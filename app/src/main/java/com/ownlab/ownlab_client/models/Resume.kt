package com.ownlab.ownlab_client.models

import com.google.gson.annotations.SerializedName

data class Resume(
    @SerializedName("name") val name: String,
    @SerializedName("tel") val tel: String,
    @SerializedName("email") val email: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("sex") val sex: String,
    @SerializedName("address") val address: String,
    @SerializedName("resumeTitle") val resumeTitle: String,
    @SerializedName("school") val school: String,
    @SerializedName("schoolState") val schoolState: String,
    @SerializedName("companyCareer") val companyCareer: String,
    @SerializedName("partCareer") val partCareer: String,
    @SerializedName("workTime") val workTime: String,
    @SerializedName("workStart") val workStart: String,
    @SerializedName("workEnd") val workEnd: String,
    @SerializedName("working") val working: Boolean,
    @SerializedName("work") val work: String,
    @SerializedName("sido") val sido: String,
    @SerializedName("sigungu") val sigungu: String,
    @SerializedName("firstWork") val firstWork: String,
    @SerializedName("secondWork") val secondWork: String,
    @SerializedName("workType") val workType: String,
    @SerializedName("wishWorkTerm") val wishWorkTerm: String,
    @SerializedName("wishWorkTermEtc") val wishWorkTermEtc: String,
    @SerializedName("wishSalaryType") val wishSalaryType: String,
    @SerializedName("ps") val ps: String,
    @SerializedName("openPermission") val openPermission: Boolean
)
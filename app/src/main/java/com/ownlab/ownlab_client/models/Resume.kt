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
    @SerializedName("openPermission") val openPermission: Boolean,
)
fun mapJsonArrayToResume(resumeList: List<Any>): List<Resume> {
    val resumes = mutableListOf<Resume>()

    val resumeChunks = resumeList.chunked(26)

    resumeChunks.forEach { chunk ->
        val safeChunk = chunk.map { item ->
            when (item) {
                is String -> item
                is Double -> item.toInt().toString() // Double을 String으로 변환
                is Int -> item.toString() // Int를 String으로 변환
                else -> "" // 알 수 없는 타입에 대해 빈 문자열을 사용
            }
        }

        val resume = Resume(
            name = safeChunk.getOrElse(0) { "" },
            tel = safeChunk.getOrElse(1) { "" },
            email = safeChunk.getOrElse(2) { "" },
            birth = safeChunk.getOrElse(3) { "" },
            sex = safeChunk.getOrElse(4) { "" },
            address = safeChunk.getOrElse(5) { "" },
            school = safeChunk.getOrElse(6) { "" },
            schoolState = safeChunk.getOrElse(7) { "" },
            companyCareer = safeChunk.getOrElse(8) { "" },
            partCareer = safeChunk.getOrElse(9) { "" },
            workTime = safeChunk.getOrElse(10) { "" },
            workStart = safeChunk.getOrElse(11) { "" },
            workEnd = safeChunk.getOrElse(12) { "" },
            working = safeChunk.getOrElse(13) { "0" }.toIntOrNull() == 1,
            work = safeChunk.getOrElse(14) { "" },
            sido = safeChunk.getOrElse(15) { "" },
            sigungu = safeChunk.getOrElse(16) { "" },
            firstWork = safeChunk.getOrElse(17) { "" },
            secondWork = safeChunk.getOrElse(18) { "" },
            workType = safeChunk.getOrElse(19) { "" },
            wishWorkTerm = safeChunk.getOrElse(20) { "" },
            wishWorkTermEtc = safeChunk.getOrElse(21) { "" },
            ps = safeChunk.getOrElse(22) { "" },
            openPermission = safeChunk.getOrElse(23) { "0" }.toIntOrNull() == 1,
            resumeTitle = safeChunk.getOrElse(25) { "" },
            wishSalaryType = safeChunk.getOrElse(26) { "" },
            )
        resumes.add(resume)
    }

    return resumes
}
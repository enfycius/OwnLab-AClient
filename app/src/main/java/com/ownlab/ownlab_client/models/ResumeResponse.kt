package com.ownlab.ownlab_client.models

data class ResumeResponse(
    val statusCode: Int,
    val message: String,
    val resumes: List<Resume> = emptyList()
)
package com.example.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    @SerialName("login")
    val name: String?,
    @SerialName("avatar_url")
    val avatarUrl: String?
)
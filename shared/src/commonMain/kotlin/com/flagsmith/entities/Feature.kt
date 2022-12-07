package com.flagsmith.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Feature(
    val id: Long,
    val name: String,
    @SerialName("created_date") val createdDate: String,
    val description: String,
    @SerialName("initial_value") val initialValue: String,
    @SerialName("default_enabled") val defaultEnabled: Boolean,
    val type: String,
)

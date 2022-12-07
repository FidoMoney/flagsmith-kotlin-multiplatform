package com.flagsmith.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Feature(
    val id: Long,
    val name: String,
    @SerialName("created_date") val createdDate: String? = null,
    val description: String? = null,
    @SerialName("initial_value") val initialValue: String? = null,
    @SerialName("default_enabled") val defaultEnabled: Boolean? = null,
    val type: String,
)

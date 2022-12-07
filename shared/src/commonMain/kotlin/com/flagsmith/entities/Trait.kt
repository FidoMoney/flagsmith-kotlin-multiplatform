package com.flagsmith.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Trait(
    val identifier: String? = null,
    @SerialName(value = "trait_key") val key: String,
    @SerialName(value = "trait_value") val value: String
)

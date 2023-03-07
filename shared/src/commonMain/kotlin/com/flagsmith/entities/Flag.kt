package com.flagsmith.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Flag(
    val feature: Feature,
    @SerialName(value = "feature_state_value")
    val featureStateValue: JsonElement? = null,
    val enabled: Boolean
)

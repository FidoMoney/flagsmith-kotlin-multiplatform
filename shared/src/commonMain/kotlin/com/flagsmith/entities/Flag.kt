package com.flagsmith.entities

import com.flagsmith.utils.serializer.FeatureStateValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Flag(
    val feature: Feature,
    @SerialName(value = "feature_state_value")
    @Serializable(FeatureStateValueSerializer::class)
    val featureStateValue: Any?,
    val enabled: Boolean,
)

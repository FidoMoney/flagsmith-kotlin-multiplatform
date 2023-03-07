package com.flagsmith.entities

import kotlinx.serialization.Serializable

@Serializable
data class IdentityWithTraits(
    val identifier: String,
    val traits: List<Trait>
)

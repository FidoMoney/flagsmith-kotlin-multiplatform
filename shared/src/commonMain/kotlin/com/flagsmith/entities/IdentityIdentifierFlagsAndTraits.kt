package com.flagsmith.entities

import kotlinx.serialization.Serializable

@Serializable
data class IdentityIdentifierFlagsAndTraits(
    val identifier: String,
    val flags: ArrayList<Flag>,
    val traits: ArrayList<Trait>
)

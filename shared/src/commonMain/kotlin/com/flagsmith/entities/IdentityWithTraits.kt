package com.flagsmith.entities

data class IdentityWithTraits(
    val identifier: String,
    val traits: List<Trait>
)

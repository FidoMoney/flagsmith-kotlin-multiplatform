package com.flagsmith

import com.flagsmith.entities.Flag
import com.flagsmith.entities.IdentityFlagsAndTraits
import com.flagsmith.entities.IdentityIdentifierFlagsAndTraits
import com.flagsmith.entities.Trait
import com.flagsmith.entities.TraitWithIdentifier
import com.flagsmith.entities.TraitWithIdentity

internal interface FlagsmithSDK {
    suspend fun getFeatureFlags(): List<Flag>
    suspend fun getFeatureFlags(identity: String?): List<Flag>
    suspend fun hasFeatureFlag(forFeatureId: String): Boolean
    suspend fun hasFeatureFlag(forFeatureId: String, identity: String?): Boolean
    suspend fun getValueForFeature(searchFeatureId: String): Any?
    suspend fun getValueForFeature(searchFeatureId: String, identity: String?): Any?
    suspend fun getTrait(id: String, identity: String): TraitWithIdentifier?
    suspend fun getTraits(identity: String): List<TraitWithIdentifier>
    suspend fun setTrait(trait: TraitWithIdentifier, identity: String): TraitWithIdentity
    suspend fun setIdentityWithTraits(identity: String, traits: List<Trait>): IdentityIdentifierFlagsAndTraits
    suspend fun getIdentity(identity: String): IdentityFlagsAndTraits
}

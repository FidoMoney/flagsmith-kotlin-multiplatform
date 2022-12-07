package com.flagsmith

import com.flagsmith.entities.Flag
import com.flagsmith.entities.IdentityFlagsAndTraits
import com.flagsmith.entities.Trait
import com.flagsmith.entities.TraitWithIdentity

interface FlagsmithSDK {
    suspend fun getFeatureFlags(identity: String?): List<Flag>
    suspend fun hasFeatureFlag(forFeatureId: String, identity: String?): Boolean
    suspend fun getValueForFeature(searchFeatureId: String, identity: String?): Any?
    suspend fun getTrait(id: String, identity: String): Trait?
    suspend fun getTraits(identity: String): List<Trait>
    suspend fun setTrait(trait: Trait, identity: String): TraitWithIdentity
    suspend fun getIdentity(identity: String): IdentityFlagsAndTraits
}

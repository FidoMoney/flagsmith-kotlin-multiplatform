package com.flagsmith

import com.flagsmith.client.FlagsmithApi
import com.flagsmith.client.FlagsmithApiImpl
import com.flagsmith.client.HttpClientBuilder
import com.flagsmith.client.HttpClientFactoryImpl
import com.flagsmith.entities.Flag
import com.flagsmith.entities.IdentityFlagsAndTraits
import com.flagsmith.entities.Trait
import com.flagsmith.entities.TraitWithIdentity

class Flagsmith(
    private val environmentKey: String,
    private val baseUrl: String = DEFAULT_BASE_URL,
    private val enableAnalytics: Boolean = DEFAULT_ENABLE_ANALYTICS,
    private val analyticsFlushPeriod: Int = DEFAULT_ANALYTICS_FLUSH_PERIOD_SECONDS
) : FlagsmithSDK {
    private val api: FlagsmithApi by lazy {
        FlagsmithApiImpl(
            httpClient = HttpClientBuilder(
                clientFactory = HttpClientFactoryImpl(),
            ).build(
                baseUrl = baseUrl,
                environmentKey = environmentKey,
            )
        )
    }

    override suspend fun getFeatureFlags(identity: String?): List<Flag> {
        return if (identity == null) {
            api.getFlags()
        } else {
            api.getIdentityFlagsAndTraits(identity).flags
        }
    }

    override suspend fun hasFeatureFlag(forFeatureId: String, identity: String?): Boolean {
        return getFeatureFlags(identity).any {
            it.feature.name == forFeatureId && it.enabled
        }
    }

    override suspend fun getValueForFeature(searchFeatureId: String, identity: String?): Any? {
        return getFeatureFlags(identity).find {
            it.feature.name == searchFeatureId && it.enabled
        }?.featureStateValue
    }

    override suspend fun getTrait(id: String, identity: String): Trait? {
        return api.getIdentityFlagsAndTraits(identity).traits.find { it.key == id }
    }

    override suspend fun getTraits(identity: String): List<Trait> {
        return api.getIdentityFlagsAndTraits(identity).traits
    }

    override suspend fun setTrait(trait: Trait, identity: String): TraitWithIdentity {
        return api.setTrait(trait, identity)
    }

    override suspend fun getIdentity(identity: String): IdentityFlagsAndTraits {
        return api.getIdentityFlagsAndTraits(identity)
    }

    companion object {
        const val DEFAULT_BASE_URL = "https://edge.api.flagsmith.com/api/v1"
        const val DEFAULT_ENABLE_ANALYTICS = true
        const val DEFAULT_ANALYTICS_FLUSH_PERIOD_SECONDS = 10
    }
}

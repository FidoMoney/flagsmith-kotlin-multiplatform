package com.flagsmith

import com.flagsmith.client.FlagsmithApi
import com.flagsmith.client.FlagsmithApiImpl
import com.flagsmith.client.HttpClientBuilder
import com.flagsmith.client.HttpClientFactoryImpl
import com.flagsmith.entities.Flag
import com.flagsmith.entities.IdentityFlagsAndTraits
import com.flagsmith.entities.IdentityIdentifierFlagsAndTraits
import com.flagsmith.entities.Trait
import com.flagsmith.entities.TraitWithIdentifier
import com.flagsmith.entities.TraitWithIdentity
import io.ktor.client.plugins.logging.LogLevel

class Flagsmith(
    private val environmentKey: String,
    private val isHttps: Boolean = DEFAULT_IS_HTTPS,
    private val baseUrl: String = DEFAULT_BASE_URL,
    private val apiPath: String = DEFAULT_API_PATH,
    private val apiVersion: String = DEFAULT_API_VERSION,
    private val enableAnalytics: Boolean = DEFAULT_ENABLE_ANALYTICS,
    private val analyticsFlushPeriod: Int = DEFAULT_ANALYTICS_FLUSH_PERIOD_SECONDS,
    private val logLevel: LogLevel = DEFAULT_LOG_LEVEL
) : FlagsmithSDK {
    private val api: FlagsmithApi by lazy {
        FlagsmithApiImpl(
            httpClient = HttpClientBuilder(
                clientFactory = HttpClientFactoryImpl()
            ).build(
                isHttps = isHttps,
                baseUrl = baseUrl,
                apiPath = apiPath,
                apiVersion = apiVersion,
                environmentKey = environmentKey,
                logLevel = logLevel
            )
        )
    }

    override suspend fun getFeatureFlags(): List<Flag> = getFeatureFlags(null)

    override suspend fun getFeatureFlags(identity: String?): List<Flag> {
        return if (identity == null) {
            api.getFlags()
        } else {
            api.getIdentityFlagsAndTraits(identity).flags
        }
    }

    override suspend fun hasFeatureFlag(forFeatureId: String): Boolean =
        hasFeatureFlag(forFeatureId, null)

    override suspend fun hasFeatureFlag(forFeatureId: String, identity: String?): Boolean {
        return getFeatureFlags(identity).any {
            it.feature.name == forFeatureId && it.enabled
        }
    }

    override suspend fun getValueForFeature(searchFeatureId: String): Any? =
        getValueForFeature(searchFeatureId, null)

    override suspend fun getValueForFeature(searchFeatureId: String, identity: String?): Any? {
        return getFeatureFlags(identity).find {
            it.feature.name == searchFeatureId && it.enabled
        }?.featureStateValue
    }

    override suspend fun getTrait(id: String, identity: String): TraitWithIdentifier? {
        return api.getIdentityFlagsAndTraits(identity).traits.find { it.key == id }
    }

    override suspend fun getTraits(identity: String): List<TraitWithIdentifier> {
        return api.getIdentityFlagsAndTraits(identity).traits
    }

    override suspend fun setTrait(trait: TraitWithIdentifier, identity: String): TraitWithIdentity {
        return api.setTrait(trait, identity)
    }

    override suspend fun setIdentityWithTraits(
        identity: String,
        traits: List<Trait>
    ): IdentityIdentifierFlagsAndTraits {
        return api.setIdentityWithTraits(identity, traits)
    }

    override suspend fun getIdentity(identity: String): IdentityFlagsAndTraits {
        return api.getIdentityFlagsAndTraits(identity)
    }

    companion object {
        const val DEFAULT_IS_HTTPS = true
        const val DEFAULT_BASE_URL = "edge.api.flagsmith.com"
        const val DEFAULT_API_PATH = "api"
        const val DEFAULT_API_VERSION = "v1"
        const val DEFAULT_ENABLE_ANALYTICS = true
        const val DEFAULT_ANALYTICS_FLUSH_PERIOD_SECONDS = 10
        val DEFAULT_LOG_LEVEL = LogLevel.NONE
    }
}

package com.flagsmith.client

import com.flagsmith.entities.Flag
import com.flagsmith.entities.Identity
import com.flagsmith.entities.IdentityFlagsAndTraits
import com.flagsmith.entities.Trait
import com.flagsmith.entities.TraitWithIdentity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal interface FlagsmithApi {
    suspend fun getIdentityFlagsAndTraits(identity: String): IdentityFlagsAndTraits
    suspend fun getFlags(): List<Flag>
    suspend fun setTrait(trait: Trait, identity: String): TraitWithIdentity
    suspend fun postAnalytics(eventMap: Map<String, Int?>)
}

internal class FlagsmithApiImpl(
    private val httpClient: HttpClient,
) : FlagsmithApi {

    override suspend fun getIdentityFlagsAndTraits(identity: String): IdentityFlagsAndTraits {
        return httpClient.get("identities") {
            parameter("identifier", identity)
        }.body()
    }


    override suspend fun getFlags(): List<Flag> {
        return httpClient.get("flags").body()
    }

    override suspend fun setTrait(trait: Trait, identity: String): TraitWithIdentity {
        return httpClient.post("traits") {
            parameter("identifier", identity)

            setBody(
                TraitWithIdentity(
                    key = trait.key,
                    value = trait.value,
                    identity = Identity(identity),
                )
            )
        }.body()
    }

    override suspend fun postAnalytics(eventMap: Map<String, Int?>) {
        httpClient.post("analytics/flags") {
            setBody(eventMap)
        }
    }
}

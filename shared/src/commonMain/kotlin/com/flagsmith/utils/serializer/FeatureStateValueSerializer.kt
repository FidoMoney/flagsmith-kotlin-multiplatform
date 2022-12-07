package com.flagsmith.utils.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object FeatureStateValueSerializer : KSerializer<Any?> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("feature_state_value", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Any? {
        val value = decoder.decodeString()
        return value
    }

    override fun serialize(encoder: Encoder, value: Any?) {
        encoder.encodeString(value = value.toString())
    }
}

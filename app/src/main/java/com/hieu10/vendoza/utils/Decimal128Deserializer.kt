package com.hieu10.vendoza.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class Decimal128Deserializer : JsonDeserializer<Double> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Double {
        return when {
            json.isJsonPrimitive && json.asJsonPrimitive.isNumber -> json.asDouble
            json.isJsonPrimitive && json.asJsonPrimitive.isString -> json.asString.toDoubleOrNull() ?: 0.0
            json.isJsonObject -> {
                val obj = json.asJsonObject
                if (obj.has($$"$numberDecimal")) {
                    obj.get($$"$numberDecimal").asString.toDoubleOrNull() ?: 0.0
                } else 0.0
            }
            else -> 0.0
        }
    }
}
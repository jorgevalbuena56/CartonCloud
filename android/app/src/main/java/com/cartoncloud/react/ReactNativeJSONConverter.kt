package com.cartoncloud.react

import com.facebook.react.bridge.WritableArray
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableNativeArray
import com.facebook.react.bridge.WritableNativeMap
import org.json.JSONArray
import org.json.JSONObject

/**
 * Converter used to send the information obtain from the backend to a format that react views understand
 */
object ReactNativeJSONConverter {
    fun convertJsonToMap(jsonObject: JSONObject): WritableMap {
        val map = WritableNativeMap()
        val iterator = jsonObject.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            val value = jsonObject.get(key)
            when (value) {
                is JSONObject -> map.putMap(key, convertJsonToMap(value))
                is JSONArray -> map.putArray(key, convertJsonToArray(value))
                is Boolean -> map.putBoolean(key, value)
                is Int -> map.putInt(key, value)
                is Double -> map.putDouble(key, value)
                is String -> map.putString(key, value)
                else -> map.putString(key, value.toString())
            }
        }
        return map
    }

    private fun convertJsonToArray(jsonArray: JSONArray): WritableArray {
        val array = WritableNativeArray()

        (0 until jsonArray.length())
                .map { jsonArray.get(it) }
                .forEach {
                    when (it) {
                        is JSONObject -> array.pushMap(convertJsonToMap(it))
                        is JSONArray -> array.pushArray(convertJsonToArray(it))
                        is Boolean -> array.pushBoolean(it)
                        is Int -> array.pushInt(it)
                        is Double -> array.pushDouble(it)
                        is String -> array.pushString(it)
                        else -> array.pushString(it.toString())
                    }
                }
        return array
    }
}
package com.example.vinilos.utils

import org.json.JSONException
import org.json.JSONObject

object SimplifiedMessage {
    fun get(stringMessage: String): HashMap<String, String> {
        val messages = HashMap<String, String>()
        val jsonObject = JSONObject(stringMessage)

        try {
            val jsonMessage = jsonObject.getJSONObject("mensaje")
            jsonMessage.keys().forEach { messages[it] = jsonMessage.getString(it) }
        } catch (e: JSONException) {
            messages["mensaje"] = jsonObject.getString("mensaje")
        }
        return messages
    }
}
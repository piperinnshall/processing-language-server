package piperinnshall.processingLanguageServer

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

@Serializable
data class MessageProtocol(
    @SerialName("jsonrpc") val jsonRpcVersion: String,
    @SerialName("id") val requestId: Int,
    @SerialName("method") val methodName: String,
    @SerialName("params") val parameters: Boolean
)


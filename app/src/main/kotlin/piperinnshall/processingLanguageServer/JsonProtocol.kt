package piperinnshall.processingLanguageServer

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@Serializable
data class Message(
    val jsonrpc: String,
    val id: Int,
    val method: String,
    val params: Boolean
) 

class EncodeMessage(val msg: Message) {
    val encodedMessage: String
        get() = "Content-Length: ${Json.encodeToString(msg).toByteArray(Charsets.UTF_8).size}\r\n\r\n${Json.encodeToString(msg)}"
}

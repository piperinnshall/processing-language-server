package piperinnshall.processingLanguageServer

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlin.runCatching

@Serializable
data class BaseProtocol(
    val jsonrpc: String,
    val id: Int,
    val method: String,
    val params: Boolean
) 

val encode: (BaseProtocol) -> String = { msg ->
    val json = Json.encodeToString(msg)
    "Content-Length: ${json.toByteArray(Charsets.UTF_8).size}\r\n\r\n${json}"
}

val decode: (ByteArray) -> Int = { msgBytes ->
    val delimiter = "\r\n\r\n".toByteArray(Charsets.UTF_8)
    val delimiterSize = delimiter.size

    val result: Triple<IntRange, IntRange, Boolean> = msgBytes.withIndex()
    .windowed(delimiterSize)
    .indexOfFirst { window ->
        val (index, _) = window.first()
        msgBytes.sliceArray(index until index + delimiterSize).contentEquals(delimiter)
    }.takeIf { it != -1 } ?.let { index ->
        Triple(0 until index, (index + delimiterSize) until msgBytes.size, true)
    } ?: Triple(IntRange.EMPTY, IntRange.EMPTY, false)

    if (!result.third) {
        throw IllegalArgumentException("Delimiter not found in message bytes")
    }

    // Content-Length: <number>
    val headerBytes = msgBytes.sliceArray(result.first)
    val headerLen = result.first.count()
    
    // Json
    val contentBytes = msgBytes.sliceArray(result.second)
    val contentLen = result.second.count()
     
    contentLen
}

package piperinnshall.processingLanguageServer

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

val splitBytes: (ByteArray, ByteArray) -> Triple<IntRange, IntRange, Boolean> = { msgBytes, delimiter ->
    val delimiterSize = delimiter.size
    msgBytes.withIndex()
    .windowed(delimiterSize)
    .indexOfFirst { window ->
        val (index, _) = window.first()
        msgBytes.sliceArray(index until index + delimiterSize).contentEquals(delimiter)
    }.takeIf { it != -1 } ?.let { index ->
        Triple(0 until index, (index + delimiterSize) until msgBytes.size, true)
    } ?: Triple(IntRange.EMPTY, IntRange.EMPTY, false)
}

val encode: (MessageProtocol) -> String = { msg ->
    val json = Json.encodeToString(msg)

    "Content-Length: ${json.toByteArray(Charsets.UTF_8).size}\r\n\r\n${json}"
}

val decode: (ByteArray) -> Pair<String, Int> = { msgBytes ->
    val result = splitBytes(msgBytes, "\r\n\r\n".toByteArray(Charsets.UTF_8))

    if (!result.third) {
        throw IllegalArgumentException("Delimiter not found in message bytes")
    }

    // Content-Length: <number>
    val headerBytes = msgBytes.sliceArray(result.first)
    val prefixLength = "Content-Length: ".toByteArray(Charsets.UTF_8).size
    val contentLength = headerBytes.sliceArray(prefixLength until headerBytes.size)
    .toString(Charsets.UTF_8).toInt()
    
    // Json Protocol 
    val contentBytes = msgBytes.sliceArray(result.second)
    val contentString = contentBytes.toString(Charsets.UTF_8)
    val message: MessageProtocol = Json.decodeFromString(contentString)
     
    Pair(message.methodName, contentLength) 
}

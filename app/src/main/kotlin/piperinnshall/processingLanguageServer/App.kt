package piperinnshall.processingLanguageServer

fun test() {
    val test = MessageProtocol(jsonRpcVersion = "2.0", requestId = 1, methodName = "textDocument/completion", parameters = true)
    val encoded: String = encode(test)
    println(encode)
    val decoded = decode(encoded.toByteArray())
    println(decoded.first)
    println(decoded.second)
}

fun handleMessage() {
}

fun main() {
}

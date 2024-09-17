package piperinnshall.processingLanguageServer

import kotlin.test.Test
import kotlin.test.assertEquals


class AppTest {
    val msg = MessageProtocol(
        jsonRpcVersion = "2.0", 
        requestId = 1,
        methodName = "textDocument/completion",
        parameters = true,
    )

    @Test
    fun TestEncode() {
        val json = """{"jsonrpc":"2.0","id":1,"method":"textDocument/completion","params":true}"""
        val expected = "Content-Length: 73\r\n\r\n$json"
        val actual = encode(msg)

        assertEquals(expected, actual)
    }

    @Test
    fun TestDecode(){
        val json = """{"jsonrpc":"2.0","id":1,"method":"text/completion","params":true}"""
        val incoming = "Content-Length: 65\r\n\r\n$json".toByteArray()
        val actual = decode(incoming)
        val contentLength = actual.second
        val contentMethodName = actual.first

        assertEquals(contentLength, 65)
        assertEquals(contentMethodName, "text/completion")
    }
}

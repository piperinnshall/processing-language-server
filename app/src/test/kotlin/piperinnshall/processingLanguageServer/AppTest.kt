package piperinnshall.processingLanguageServer

import kotlin.test.Test
import kotlin.test.assertEquals

val msg = BaseProtocol(
    jsonrpc = "2.0", 
    id = 1, 
    method = "textDocument/completion", 
    params = true
)

class AppTest {
    @Test
    fun TestEncode() {
        val json = """{"jsonrpc":"2.0","id":1,"method":"textDocument/completion","params":true}"""
        val expected = "Content-Length: 73\r\n\r\n$json"
        val actual: String = encode(msg)

        assertEquals(expected, actual)
    }

    @Test
    fun TestDecode (){
        val json = """{"jsonrpc":"2.0","id":1,"method":"textDocument/completion","params":true}"""
        val incoming = "Content-Length: 73\r\n\r\n$json"
        val contentLen = decode(incoming.toByteArray()) 

        assertEquals(contentLen, 73)
    }
}

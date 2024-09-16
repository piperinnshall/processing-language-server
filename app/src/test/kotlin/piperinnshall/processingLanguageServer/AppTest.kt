package piperinnshall.processingLanguageServer

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertEquals

val message = Message(
    jsonrpc = "2.0", 
    id = 1, 
    method = "textDocument/completion", 
    params = true
)

class AppTest {
    @Test
    fun hasEncode() {
        val expected: String = """Content-Length: 73

{"jsonrpc":"2.0","id":1,"method":"textDocument/completion","params":true}"""

        val actual: String = EncodeMessage(message).encodedMessage
        val normalizedActual = actual.replace("\r\n", "\n")

        assertEquals(expected, normalizedActual)
    }
}


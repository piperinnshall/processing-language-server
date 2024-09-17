package piperinnshall.processingLanguageServer

fun handleMessage(logger: LogHelper, messageBytes: ByteArray) {
    logger.print(messageBytes.toString(Charsets.UTF_8))
}

fun processInputFromStdin() {
    val logger = log("log.txt")
    val inputStream = System.`in`.buffered()
    var currentData = byteArrayOf()

    while (true) {
        val bytesRead = inputStream.readNBytes(4096)

        if (bytesRead.isEmpty()) {
            continue
        }

        currentData += bytesRead

        try {
            while (true) {
                val (messageLength, messageBytes) = splitMessages(currentData, true)

                if (messageLength == -1) {
                    break
                }

                handleMessage(logger, messageBytes)

                currentData = currentData.sliceArray(messageLength until currentData.size)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun main() {
    processInputFromStdin()
}

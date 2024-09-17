package piperinnshall.processingLanguageServer

import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LogHelper(private val fileName: String) {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    init {
        File(fileName).printWriter().use { it.println("Log started at ${LocalDateTime.now()}") }
    }

    fun print(message: String) {
        val timestamp = LocalDateTime.now().format(dateTimeFormatter)
        val logMessage = "$timestamp - $message"

        FileWriter(fileName, false).use { writer ->
            PrintWriter(writer).use { pw ->
                pw.println(logMessage)
            }
        }
    }
}

val log: (String) -> LogHelper = { fileName ->
    LogHelper(fileName)
}


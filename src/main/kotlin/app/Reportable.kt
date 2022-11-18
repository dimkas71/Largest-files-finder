package app

import app.Reportable.Companion.END_LINE
import java.io.OutputStream

interface Reportable {
    fun report(stream: OutputStream, results: List<FileInfo>)

    enum class ReportType {
        CONSOLE,
        CSV
    }

    interface Resolver {
        fun resolve(type: ReportType): Reportable
    }

    class ResolverImpl: Resolver {
        override fun resolve(type: ReportType): Reportable = when (type) {
            ReportType.CONSOLE -> ConsoleReportable()
            ReportType.CSV -> CsvReportable()
        }

    }

    companion object {
        const val END_LINE = "\n"
    }


}

class ConsoleReportable: Reportable {
    private val separator = "\t"
    override fun report(stream: OutputStream, results: List<FileInfo>) {
        //Header
        val header = "        Dir             $separator         name          $separator        size ir (kBytes)${END_LINE}"
        stream.write(header.toByteArray())
        results.forEach {
            val content = "${it.dir}${separator}${it.fileName}${separator}${it.sizeInKB}KBytes${END_LINE}"
            stream.write(content.toByteArray())
        }
    }

}

class CsvReportable: Reportable {
    private val separator = ";"
    override fun report(stream: OutputStream, results: List<FileInfo>) {
        val header = "Dir${separator}File name${separator}size in kBytes${separator}${END_LINE}"
        stream.write(header.toByteArray())

        results.forEach {
            val content = "${it.dir}${separator}${it.fileName}${separator}${it.sizeInKB}${separator}${END_LINE}"
            stream.write(content.toByteArray())
        }
    }

}
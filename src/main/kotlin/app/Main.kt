import app.*
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import kotlin.io.path.fileSize
import kotlin.io.path.name

fun main(args: Array<String>) {

    val options = Parser.parse(args.joinToString(" "))

    val baseDir = options[Options.INPUT_DIR] as String
    val take = options[Options.OUTPUT_SIZE] as Int

    kotlin.math.round(1.23f)
    var fileInfo = emptyList<FileInfo>()
    Files.walkFileTree(Path.of(baseDir), object: FileVisitor<Path> {
        override fun visitFile(file: Path?, attrs: BasicFileAttributes?): FileVisitResult {
            fileInfo += FileInfo(
                file?.parent?.toFile()?.parent ?: "",
                file?.name ?: "",
                ((file?.fileSize()?.toFloat() ?: 0.0f) / BYTES_IN_KB).round(2) )
            return FileVisitResult.CONTINUE
        }
        override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes?): FileVisitResult = FileVisitResult.CONTINUE
        override fun visitFileFailed(file: Path?, exc: IOException?):FileVisitResult = FileVisitResult.CONTINUE
        override fun postVisitDirectory(dir: Path?, exc: IOException?): FileVisitResult = FileVisitResult.CONTINUE
    })

    val results = fileInfo
            .filter { it.dir.isNotEmpty() }
            .sortedBy { -it.sizeInKB }
            .take(take)


    val reportTypeAsString = options[Options.REPORT_TYPE]

    val resolver = Reportable.ResolverImpl()
    var stream: OutputStream = System.out
    val reportable: Reportable = when(reportTypeAsString) {
        "console" -> resolver.resolve(Reportable.ReportType.CONSOLE)
        "csv" -> {
            stream = FileOutputStream(Path.of(options[Options.INPUT_DIR] as String, options[Options.OUTPUT_FILE] as String).toFile())
            resolver.resolve(Reportable.ReportType.CSV)
        }
        else -> resolver.resolve(Reportable.ReportType.CONSOLE)
    }

    stream.use {
        reportable.report(it, results)
    }

}
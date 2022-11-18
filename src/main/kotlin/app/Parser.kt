package app

object Parser {

    fun parse(args: String): Map<Options, Any> {
        var map = mutableMapOf<Options, Any>(
            Options.OUTPUT_SIZE to Options.OUTPUT_SIZE.defaultValue.toInt(),
            Options.REPORT_TYPE to Options.REPORT_TYPE.defaultValue,
            Options.INPUT_DIR to Options.INPUT_DIR.defaultValue,
            Options.OUTPUT_FILE to Options.OUTPUT_FILE.defaultValue
        )
        if (args.isEmpty()) return map

        val split = args.split(" ")

        val keys = split.filterIndexed { index, _ ->
            index % 2 == 0
        }.toList()

        val values = split.filterIndexed { index, _ ->
            index % 2 != 0
        }
        val pairs: List<Pair<String, String>> = keys.zip(values)

        pairs.forEach { pair ->
            val (key, value) = pair
            when (key) {
                "-h" -> map[Options.HELP] = value
                "-d" -> map[Options.INPUT_DIR] = value
                "-n" -> map[Options.OUTPUT_SIZE] = value.toInt()
                "-o" -> map[Options.OUTPUT_FILE] = value
                "-r" -> map[Options.REPORT_TYPE] = value
                else -> {

                    throw UnsupportedOperationException("""
                        Option with $key does not supported
                        $MESSAGE
                        """.trimIndent())
                }
            }
        }


        return map.toMap()
    }

    const val MESSAGE = """
                        Usage: 
                        1. program -h print help message
                        2. program -d path -n 100 -o test.csv -r csv
                            -d path - set up base directory for file finding
                            -n 100 - top 100 files with max size sorted by descending order.
                            -o csv_file - output to file with name csv_file
                            -r report_type, supported two types with values: console, csv 
                    """


}

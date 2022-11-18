package app

enum class Options(val key: String, val defaultValue: String) {
    HELP("-h", "help"),
    INPUT_DIR("-d", "."), //use current directory "."
    OUTPUT_SIZE("-n", "25"), //25 string to output on a console screen
    OUTPUT_FILE("-o", "file.csv"), // file with name file.csv in current dir "."
    REPORT_TYPE("-r", "console"); //possible values: console; csv
}

package app.test

import app.Options
import app.Parser
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class ParserTest {

    //region Check default values
    @Test
    fun `test default values if a args is an empty string`() {
        val args = ""
        val options = Parser.parse(args)

        assertEquals(options[Options.INPUT_DIR], Options.INPUT_DIR.defaultValue)
        assertEquals(options[Options.OUTPUT_SIZE], Options.OUTPUT_SIZE.defaultValue.toInt())
        assertEquals(options[Options.OUTPUT_FILE], Options.OUTPUT_FILE.defaultValue)
    }
//endregion

    //region check 1 option others are default
    @Test
    fun `an option -d path should be parsed properly and others to defaults`() {
        val args = "-d /home/dimkas71"
        val options: Map<Options, Any> = Parser.parse(args)

        assertEquals(options[Options.INPUT_DIR], "/home/dimkas71")
        assertEquals(options[Options.OUTPUT_SIZE], Options.OUTPUT_SIZE.defaultValue.toInt())
        assertEquals(options[Options.OUTPUT_FILE], Options.OUTPUT_FILE.defaultValue)
    }

    @Test
    fun `an option -n 100 should be parsed properly and others to defaults`() {
        val args = "-n 100"
        val options: Map<Options, Any> = Parser.parse(args)

        assertEquals(options[Options.INPUT_DIR], Options.INPUT_DIR.defaultValue)
        assertEquals(options[Options.OUTPUT_SIZE], 100)
        assertEquals(options[Options.OUTPUT_FILE], Options.OUTPUT_FILE.defaultValue)
    }

    @Test
    fun `an option -o path should be parsed properly and other to defaults`() {
        val args = "-o test.csv"
        val options: Map<Options, Any> = Parser.parse(args)

        assertEquals(options[Options.INPUT_DIR], Options.INPUT_DIR.defaultValue)
        assertEquals(options[Options.OUTPUT_SIZE], Options.OUTPUT_SIZE.defaultValue.toInt())
        assertEquals(options[Options.OUTPUT_FILE], "test.csv")
    }
//endregion

    //region check 2 options another is default
    @Test
    fun `an option -d path -n 100 should be parsed properly and others to defaults`() {
        val args = "-d /home/dimkas71 -n 100"
        val options: Map<Options, Any> = Parser.parse(args)

        assertEquals(options[Options.INPUT_DIR], "/home/dimkas71")
        assertEquals(options[Options.OUTPUT_SIZE], 100)
        assertEquals(options[Options.OUTPUT_FILE], Options.OUTPUT_FILE.defaultValue)
    }
//endregion

    //region check the case with 3 options
    @Test
    fun `an option -d path -n 100 -o test_csv should be parsed properly`() {
        val args = "-d /home/dimkas71 -n 100 -o test.csv"
        val options: Map<Options, Any> = Parser.parse(args)

        assertEquals(options[Options.INPUT_DIR], "/home/dimkas71")
        assertEquals(options[Options.OUTPUT_SIZE], 100)
        assertEquals(options[Options.OUTPUT_FILE], "test.csv")
    }

    @Test
    fun `an option -d path -n 100 -o test_csv should be parsed properly if the pair order has been changed`() {
        val args = "-o test.csv -n 100 -d /home/dimkas71"
        val options: Map<Options, Any> = Parser.parse(args)

        assertEquals(options[Options.INPUT_DIR], "/home/dimkas71")
        assertEquals(options[Options.OUTPUT_SIZE], 100)
        assertEquals(options[Options.OUTPUT_FILE], "test.csv")
    }
//endregion

}
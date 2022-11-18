package app.test

import app.round
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilTest {
    @Test
    fun `2dot453 as rounded to 2 decimals should be 2dot45`() {
        assertEquals(2.34f, 2.343f.round(2))
        assertEquals(2.3f, 2.343f.round(1))
        assertEquals(2f, 2.343f.round(0))
        assertEquals(2.46f, 2.465f.round(2))

    }
}
package io.lb.presentation.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TimeExtensionsTest {
    @Test
    fun `When converting milliseconds to seconds, expect the correct value`() {
        val millis = 1730047292992
        assertEquals("27/10/2024 16:41:32", millis.toDateFormat())
    }
}

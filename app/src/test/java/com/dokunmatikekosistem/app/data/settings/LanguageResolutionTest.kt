package com.dokunmatikekosistem.app.data.settings

import org.junit.Assert.assertEquals
import org.junit.Test

class LanguageResolutionTest {

    @Test
    fun `supported languages pass through unchanged`() {
        assertEquals("en", resolveInitialLanguageTag("en"))
        assertEquals("tr", resolveInitialLanguageTag("tr"))
        assertEquals("zh", resolveInitialLanguageTag("zh"))
    }

    @Test
    fun `unsupported languages fall back to English`() {
        assertEquals("en", resolveInitialLanguageTag("de"))
        assertEquals("en", resolveInitialLanguageTag("ja"))
        assertEquals("en", resolveInitialLanguageTag("fr"))
    }

    @Test
    fun `empty and null-like inputs fall back to English`() {
        assertEquals("en", resolveInitialLanguageTag(""))
        assertEquals("en", resolveInitialLanguageTag("und"))
    }

    @Test
    fun `resolved tag is always a language DeckPad ships`() {
        val inputs = listOf("en", "tr", "zh", "de", "ja", "fr", "", "und", "zz")
        inputs.forEach { input ->
            assertEquals(
                "resolveInitialLanguageTag(\"$input\") must resolve to a shipped language",
                true,
                SUPPORTED_LANGUAGE_TAGS.contains(resolveInitialLanguageTag(input))
            )
        }
    }
}
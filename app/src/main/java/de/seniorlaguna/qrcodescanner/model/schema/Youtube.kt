package de.seniorlaguna.qrcodescanner.model.schema

import de.seniorlaguna.qrcodescanner.extension.startsWithAnyIgnoreCase

class Youtube(val url: String) : Schema {

    companion object {
        private val PREFIXES = listOf("vnd.youtube://", "http://www.youtube.com", "https://www.youtube.com")

        fun parse(text: String): Youtube? {
            if (text.startsWithAnyIgnoreCase(PREFIXES).not()) {
                return null
            }
            return Youtube(text)
        }
    }

    override val schema = BarcodeSchema.YOUTUBE
    override fun toFormattedText(): String = url
    override fun toBarcodeText(): String = url
}
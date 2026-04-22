package build.kargo.crypto

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object Encoding {

    @OptIn(ExperimentalEncodingApi::class)
    fun base64Url(bytes: ByteArray): String {
        return Base64.UrlSafe.encode(bytes)
    }

    fun base64UrlNoPadding(bytes: ByteArray): String {
        return base64Url(bytes).trimEnd('=')
    }
}
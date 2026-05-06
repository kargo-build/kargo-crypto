package builders.kargo.crypto

expect fun exRandomBytes(length: Int): ByteArray

object Crypto {

    fun randomBytes(length: Int): ByteArray = exRandomBytes(length)

    fun sha256(bytes: ByteArray): ByteArray = Sha256.hash(bytes)

    fun sha256(input: String): ByteArray = sha256(input.encodeToByteArray())

    fun hmacSha256(data: ByteArray, key: ByteArray): ByteArray = Hmac.sha256(data, key)

    fun hmacSha256(data: String, key: String): ByteArray = Hmac.sha256(data, key)

    fun hmacSha256Hex(data: String, key: String): String = Encoding.hex(hmacSha256(data, key))
}
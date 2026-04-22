package build.kargo.crypto

expect fun randomBytes(length: Int): ByteArray

object Crypto {

    fun randomBytes(length: Int): ByteArray = build.kargo.crypto.randomBytes(length)

    fun sha256(bytes: ByteArray): ByteArray = Sha256.hash(bytes)

    fun sha256(input: String): ByteArray = sha256(input.encodeToByteArray())
}
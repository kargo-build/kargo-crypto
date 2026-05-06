package builders.kargo.crypto

object Hmac {
    private const val BLOCK_SIZE = 64
    private const val IPAD: Byte = 0x36
    private const val OPAD: Byte = 0x5c

    /**
     * Computes the HMAC-SHA256 signature for the given data and key.
     */
    fun sha256(data: ByteArray, key: ByteArray): ByteArray {
        var k = if (key.size > BLOCK_SIZE) {
            Crypto.sha256(key)
        } else {
            key
        }

        if (k.size < BLOCK_SIZE) {
            k = k.copyOf(BLOCK_SIZE)
        }

        val ipad = ByteArray(BLOCK_SIZE) { i -> (k[i].toInt() xor IPAD.toInt()).toByte() }
        val opad = ByteArray(BLOCK_SIZE) { i -> (k[i].toInt() xor OPAD.toInt()).toByte() }

        // HMAC = H(K XOR opad, H(K XOR ipad, text))
        val innerHash = Crypto.sha256(ipad + data)
        return Crypto.sha256(opad + innerHash)
    }

    /**
     * Computes the HMAC-SHA256 signature for the given string data and key.
     */
    fun sha256(data: String, key: String): ByteArray =
        sha256(data.encodeToByteArray(), key.encodeToByteArray())
}

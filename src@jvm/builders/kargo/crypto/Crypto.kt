package builders.kargo.crypto

import java.security.SecureRandom

private val random = SecureRandom()

actual fun exRandomBytes(length: Int): ByteArray {
    val bytes = ByteArray(length)
    random.nextBytes(bytes)
    return bytes
}

actual fun exSignEcdsaP256(dataToSign: String, privateKeyPem: String): String {
    throw UnsupportedOperationException("ECDSA P-256 not implemented on JVM wrapper yet")
}
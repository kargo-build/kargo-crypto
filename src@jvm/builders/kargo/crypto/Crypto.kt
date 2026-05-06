package builders.kargo.crypto

import java.security.SecureRandom

private val random = SecureRandom()

actual fun exRandomBytes(length: Int): ByteArray {
    val bytes = ByteArray(length)
    random.nextBytes(bytes)
    return bytes
}
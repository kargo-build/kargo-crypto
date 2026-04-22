package build.kargo.crypto

import kotlinx.cinterop.*
import platform.posix.*

@ExperimentalForeignApi
actual fun randomBytes(length: Int): ByteArray {
    val bytes = ByteArray(length)
    val file = fopen("/dev/urandom", "rb") ?: error("Cannot open /dev/urandom")

    try {
        bytes.usePinned { pinned ->
            val read = fread(pinned.addressOf(0), 1u, length.toULong(), file)
            if (read != length.toULong()) {
                error("Failed to read enough random bytes")
            }
        }
    } finally {
        fclose(file)
    }

    return bytes
}
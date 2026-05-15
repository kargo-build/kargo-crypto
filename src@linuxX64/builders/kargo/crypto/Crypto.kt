package builders.kargo.crypto

import kotlinx.cinterop.*
import platform.posix.*
import libcrypto.*

@ExperimentalForeignApi
actual fun exRandomBytes(length: Int): ByteArray {
    val bytes = ByteArray(length)
    if (length > 0) {
        bytes.usePinned { pinned ->
            val buf = pinned.addressOf(0) as CPointer<UByteVar>
            if (RAND_bytes(buf, length) != 1) {
                error("OpenSSL RAND_bytes failed to generate random bytes")
            }
        }
    }
    return bytes
}

@ExperimentalForeignApi
actual fun exSignEcdsaP256(dataToSign: String, privateKeyPem: String): String {
    memScoped {
        val bio = BIO_new_mem_buf(privateKeyPem.cstr.ptr, -1) ?: error("BIO_new_mem_buf failed")
        val pkey = PEM_read_bio_PrivateKey(bio, null, null, null)
        BIO_free(bio)
        if (pkey == null) error("PEM_read_bio_PrivateKey failed")

        val ctx = EVP_MD_CTX_new() ?: error("EVP_MD_CTX_new failed")
        try {
            if (EVP_DigestSignInit(ctx, null, EVP_sha256(), null, pkey) <= 0) error("EVP_DigestSignInit failed")

            val dataBytes = dataToSign.encodeToByteArray()
            dataBytes.usePinned { dataPinned ->
                if (EVP_DigestSignUpdate(ctx, dataPinned.addressOf(0), dataBytes.size.toULong()) <= 0) error("EVP_DigestSignUpdate failed")
            }

            val sigLen = alloc<size_tVar>()
            if (EVP_DigestSignFinal(ctx, null, sigLen.ptr) <= 0) error("EVP_DigestSignFinal len failed")

            val sigBuf = allocArray<UByteVar>(sigLen.value.toInt())
            if (EVP_DigestSignFinal(ctx, sigBuf, sigLen.ptr) <= 0) error("EVP_DigestSignFinal failed")

            val pp = alloc<CPointerVar<UByteVar>>()
            pp.value = sigBuf
            val ecdsaSig = d2i_ECDSA_SIG(null, pp.ptr, sigLen.value.toLong()) ?: error("d2i_ECDSA_SIG failed")

            val rPtr = alloc<CPointerVar<BIGNUM>>()
            val sPtr = alloc<CPointerVar<BIGNUM>>()
            ECDSA_SIG_get0(ecdsaSig, rPtr.ptr, sPtr.ptr)

            val rawSig = ByteArray(64)
            rawSig.usePinned { pinned ->
                val rBuf = pinned.addressOf(0) as CPointer<UByteVar>
                val sBuf = pinned.addressOf(32) as CPointer<UByteVar>
                BN_bn2binpad(rPtr.value, rBuf, 32)
                BN_bn2binpad(sPtr.value, sBuf, 32)
            }

            ECDSA_SIG_free(ecdsaSig)
            
            return Encoding.base64UrlNoPadding(rawSig)
        } finally {
            EVP_MD_CTX_free(ctx)
            EVP_PKEY_free(pkey)
        }
    }
}
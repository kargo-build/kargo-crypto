package build.kargo.crypto

object Pkce {

    private const val MIN = 43
    private const val MAX = 128

    fun generateVerifier(length: Int = 64): String {
        require(length in MIN..MAX)
        return Encoding.base64UrlNoPadding(Crypto.randomBytes(length))
            .take(length)
    }

    fun generateChallenge(verifier: String) = Encoding.base64UrlNoPadding(Crypto.sha256(verifier))
}
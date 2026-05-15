# kargo-crypto

## 📦 Installation

Add the dependency to your `module.yaml` (or equivalent):

```yaml
sources:
  - github: kargo-build/kargo-crypto
    version: 1.2.0
```

> Uses Kargo dependency manager. Versions follow semantic versioning.

Core cryptographic primitives for Kotlin Multiplatform, including secure random generation, SHA-256 hashing, HMAC-SHA256, and PKCE helpers. Designed for performance, safety, and clean API design.

## ✨ Features

- Multiplatform (JVM + Native)
- Secure random generation (via OpenSSL on Linux)
- SHA-256 hashing (pure Kotlin)
- HMAC-SHA256 support
- ECDSA P-256 (ES256) signature generation (native C-interop)
- OAuth 2.0 PKCE helpers
- Hex and Base64Url encoding helpers

## 🚀 Usage

### Random bytes

```kotlin
import builders.kargo.crypto.Crypto

val bytes = Crypto.randomBytes(32)
```

---

### SHA-256

```kotlin
val hash = Crypto.sha256("hello world")
```

---

### HMAC-SHA256

```kotlin
val signature = Crypto.hmacSha256Hex("data", "secret-key")
```

---

### ECDSA P-256 Signatures (JWT/VAPID)

```kotlin
// Generates an IEEE 1363 (R|S) 64-byte signature encoded in Base64Url
val jwtSignature = Crypto.signEcdsaP256(
    dataToSign = "eyJhbGciOiJFUzI1NiJ9.eyJzdWIiOiIxMjM0In0",
    privateKeyPem = "-----BEGIN EC PRIVATE KEY-----\n..."
)
```

---

### Encoding

```kotlin
import builders.kargo.crypto.Encoding

val hexString = Encoding.hex(byteArrayOf(0xDE.toByte(), 0xAD.toByte())) // "dead"
```

---

### PKCE (OAuth 2.0)

```kotlin
import builders.kargo.crypto.Pkce

val verifier = Pkce.generateVerifier()
val challenge = Pkce.generateChallenge(verifier)
```

---

## 🧠 Design

- `randomBytes` is platform-specific (`expect/actual` using OpenSSL `RAND_bytes` on Linux)
- SHA-256 is implemented in pure Kotlin (common)
- HMAC-SHA256 is built on top of core primitives
- PKCE is built on top of core primitives
- ECDSA P-256 uses highly-optimized OpenSSL C-Interop on Native (`libcrypto`)

---

## ⚙️ Platform implementations

- JVM → `SecureRandom`
- Native (LinuxX64) → Native bindings to OpenSSL (`libcrypto`)

---

## ⚠️ Notes

- Suitable for:
    - hashing
    - tokens
    - HMAC signatures
    - OAuth PKCE

- Not intended for:
    - full cryptographic suites (AES, RSA, etc.)

---

## 🔮 Roadmap

- Additional hash algorithms (SHA-512)
- AEAD support (maybe)
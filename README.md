# kargo-crypto


## 📦 Installation

Add the dependency to your `kargo.toml` (or equivalent):

```yaml
sources:
  - github: kargo-build/kargo-env
    version: 1.0.0

  - github: kargo-build/kargo-crypto
    version: 1.0.0
```

> Uses Kargo dependency manager. Versions follow semantic versioning.

Core cryptographic primitives for Kotlin Multiplatform, including secure random generation, SHA-256 hashing, and PKCE helpers. Designed for performance, safety, and clean API design.

## ✨ Features

- Multiplatform (JVM + Native)
- Secure random generation
- SHA-256 hashing (pure Kotlin)
- OAuth 2.0 PKCE helpers
- Zero external dependencies

## 🚀 Usage

### Random bytes

```kotlin
import kargo.crypto.Crypto

val bytes = Crypto.randomBytes(32)
```

---

### SHA-256

```kotlin
val hash = Crypto.sha256("hello world")
```

---

### PKCE (OAuth 2.0)

```kotlin
import kargo.crypto.Pkce

val verifier = Pkce.generateVerifier()
val challenge = Pkce.generateChallenge(verifier)
```

---

## 🧠 Design

- `randomBytes` is platform-specific (`expect/actual`)
- SHA-256 is implemented in pure Kotlin (common)
- PKCE is built on top of core primitives
- No OpenSSL or native crypto bindings

---

## ⚙️ Platform implementations

- JVM → `SecureRandom`
- Native → system entropy source (`/dev/urandom` or equivalent)

---

## ⚠️ Notes

- Suitable for:
    - hashing
    - tokens
    - OAuth PKCE

- Not intended for:
    - full cryptographic suites (AES, RSA, etc.)

---

## 🔮 Roadmap

- HMAC support
- Additional hash algorithms (SHA-512)
- Encoding helpers
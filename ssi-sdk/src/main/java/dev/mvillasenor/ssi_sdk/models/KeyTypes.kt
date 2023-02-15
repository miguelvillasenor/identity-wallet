package dev.mvillasenor.ssi_sdk.models

enum class KeyType(val stringRepresentation: String) {
    Ed25519("Ed25519"),
    X25519("X25519"),
    SECP256k1("secp256k1"),
    P224("P-224"),
    P256("P-256"),
    P384("P-384"),
    P521("P-521"),
    RSA("RSA")
}

fun String.toKeyType(): KeyType? = when (this) {
    "Ed25519" -> KeyType.Ed25519
    "X25519" -> KeyType.X25519
    "secp256k1" -> KeyType.SECP256k1
    "P-224" -> KeyType.P224
    "P-256" -> KeyType.P256
    "P-384" -> KeyType.P384
    "P-521" -> KeyType.P521
    "RSA" -> KeyType.RSA
    else -> null
}
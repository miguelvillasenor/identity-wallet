package dev.mvillasenor.storage.db

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import dev.mvillasenor.ssi_sdk.models.DidKey
import dev.mvillasenor.storage.WalletStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class RoomWalletStore(private val db: WalletDB) : WalletStore {

    override suspend fun storeDidKey(didKey: DidKey) = withContext(Dispatchers.IO) {
        val entity = DidKeyEntity(
            did = didKey.didKey,
            encryptedJWK = cipher(didKey.base64privateJWK)
        )
        db.didKeyDao().insert(entity)
    }

    override fun observeDids(): Flow<List<String>> {
        return db.didKeyDao().observeDids()
    }

    override suspend fun getDidKey(did: String): DidKey = withContext(Dispatchers.IO) {
        val entity = db.didKeyDao().getDidKey(did)
        val tokens = entity.encryptedJWK.split(":")
        DidKey(
            entity.did,
            decipher(tokens[0], tokens[1])
        )
    }

    private fun cipher(data: String): String {
        val secretKey = getSecretKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        val iv = Base64.encodeToString(cipher.iv, Base64.DEFAULT)
        return Base64.encodeToString(
            cipher.doFinal(Base64.decode(data, Base64.DEFAULT)),
            Base64.DEFAULT
        ) + ":" + iv
    }

    private fun decipher(data: String, iv: String): String {
        val secretKey = getSecretKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val spec = GCMParameterSpec(128, Base64.decode(iv, Base64.DEFAULT))
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        return Base64.encodeToString(
            cipher.doFinal(Base64.decode(data, Base64.DEFAULT)),
            Base64.DEFAULT
        )
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null)

        try {
            val secretKeyEntry = keyStore
                .getEntry(SECRET_ALIAS, null) as KeyStore.SecretKeyEntry

            return secretKeyEntry.secretKey
        } catch (exception: java.lang.Exception) {
            val keyGenerator = KeyGenerator
                .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                SECRET_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
            keyGenerator.init(keyGenParameterSpec);
            return keyGenerator.generateKey();
        }
    }

    companion object {
        const val SECRET_ALIAS = "wallet_secret"
    }
}
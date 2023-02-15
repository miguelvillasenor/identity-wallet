package dev.mvillasenor.storage

import dev.mvillasenor.ssi_sdk.models.DidKey
import kotlinx.coroutines.flow.Flow

interface WalletStore {

    suspend fun storeDidKey(didKey: DidKey)

    fun observeDids(): Flow<List<String>>

    suspend fun getDidKey(did: String): DidKey

    suspend fun removeDid(did: String)
}
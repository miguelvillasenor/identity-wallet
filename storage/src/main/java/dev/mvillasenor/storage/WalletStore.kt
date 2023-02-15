package dev.mvillasenor.storage

import dev.mvillasenor.ssi_sdk.models.DidKey
import kotlinx.coroutines.flow.Flow

interface WalletStore {

    suspend fun storeDidKey(didKey: DidKey)

    fun observeDidKeys(): Flow<List<DidKey>>

    suspend fun getDidKey(did: String): DidKey
}
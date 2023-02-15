package dev.mvillasenor.ssi_sdk

import dev.mvillasenor.ssi_sdk.models.DidKey
import dev.mvillasenor.ssi_sdk.models.KeyType
import dev.mvillasenor.ssi_sdk.models.toDidKey
import dev.mvillasenor.ssi_sdk.models.toKeyType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ssi.Ssi

class DidService {
    fun getSupportedKeyTypes(): List<KeyType> {
        val supportedTypes = Ssi.getSupportedKeyTypes()
        val supportedTypesList = mutableListOf<KeyType>()
        for (index in 0 until supportedTypes.size()) {
            supportedTypes[index].toKeyType()?.let {
                supportedTypesList.add(it)
            }
        }
        return supportedTypesList.toList()
    }

    suspend fun generateDidKey(keyType: KeyType): DidKey = withContext(Dispatchers.IO){
        val didKeyWrapper = Ssi.generateDIDKey(keyType.stringRepresentation)
        didKeyWrapper.toDidKey()
    }

    fun isSupportedKeyType(keyType: KeyType): Boolean {
        return Ssi.isSupportedKeyType(keyType.stringRepresentation)
    }
}
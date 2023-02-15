package dev.mvillasenor.ssi_sdk

import dev.mvillasenor.ssi_sdk.models.DidKey
import dev.mvillasenor.ssi_sdk.models.KeyType
import dev.mvillasenor.ssi_sdk.models.toDidKey
import dev.mvillasenor.ssi_sdk.models.toKeyType
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

    fun generateDidKey(keyType: KeyType): DidKey {
        val didKeyWrapper = Ssi.generateDIDKey(keyType.stringRepresentation)
        return didKeyWrapper.toDidKey()
    }

    fun isSupportedKeyType(keyType: KeyType): Boolean {
        return Ssi.isSupportedKeyType(keyType.stringRepresentation)
    }
}
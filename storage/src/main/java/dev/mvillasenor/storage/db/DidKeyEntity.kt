package dev.mvillasenor.storage.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "did_key")
data class DidKeyEntity(
    @PrimaryKey val did: String,
    @ColumnInfo("jwk") val encryptedJWK: String,
)
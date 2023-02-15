package dev.mvillasenor.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DidKeyEntity::class], version = 1)
abstract class WalletDB: RoomDatabase() {
    abstract fun didKeyDao(): DidKeyDao
}
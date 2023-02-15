package dev.mvillasenor.storage.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface DidKeyDao {

    @Query("SELECT * FROM did_key")
    fun observeDidKeys(): Flow<List<DidKeyEntity>>

    @Query("SELECT did FROM did_key")
    fun observeDids(): Flow<List<String>>

    @Query("SELECT * FROM did_key WHERE did = :did LIMIT 1")
    fun getDidKey(did: String): DidKeyEntity

    @Insert
    suspend fun insert(didKey: DidKeyEntity)

    @Query("DELETE FROM did_key WHERE did = :did")
    suspend fun delete(did: String)
}
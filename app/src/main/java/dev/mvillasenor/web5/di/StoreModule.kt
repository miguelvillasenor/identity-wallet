package dev.mvillasenor.web5.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mvillasenor.storage.WalletStore
import dev.mvillasenor.storage.db.RoomWalletStore
import dev.mvillasenor.storage.db.WalletDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StoreModule {

    @Provides
    @Singleton
    fun providesDb(context: Application): WalletDB = Room
        .databaseBuilder(context, WalletDB::class.java, "wallet-db").build()

    @Provides
    @Singleton
    fun providesWalletStore(db: WalletDB): WalletStore = RoomWalletStore(db)
}
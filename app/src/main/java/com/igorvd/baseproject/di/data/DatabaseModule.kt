package com.igorvd.baseproject.di.data

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 * @author Igor Vilela
 * @since 24/01/2018
 */

@Module
class DatabaseModule {

   /* @Singleton
    @Provides
    fun providesAdvancedReceptionDatabase(mApplication: GenesisApplication): AdvancedReceptionDatabase {

        val db = Room.databaseBuilder(mApplication.applicationContext,
                AdvancedReceptionDatabase::class.java, "database-genesis2").build()

        return db
    }*/

}
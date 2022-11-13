package com.onlinetest.movielist.di

import android.content.Context
import com.onlinetest.movielist.datasource.TheDatabase
import com.onlinetest.movielist.datasource.getDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TheApplicationModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): TheDatabase =
        getDatabase(context)

    @Provides
    fun provideMovieDao(database: TheDatabase) = database.movieDao
}
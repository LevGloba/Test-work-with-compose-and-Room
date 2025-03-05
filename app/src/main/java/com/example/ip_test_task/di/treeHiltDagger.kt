package com.example.ip_test_task.di

import com.example.ip_test_task.data_layer.data_source.CreateRoomDatabaseImpl
import com.example.ip_test_task.data_layer.repositories.ContentRepositoryImpl
import com.example.ip_test_task.model.ContentRepository
import com.example.ip_test_task.model.CreateRoomDatabase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RoomInject  {

    @Binds
    @Singleton
    abstract fun provideRoomDatabase(createRoomDatabaseImpl: CreateRoomDatabaseImpl): CreateRoomDatabase

    @Binds
    @Singleton
    abstract fun provideContent(contentRepositoryImpl: ContentRepositoryImpl): ContentRepository
}
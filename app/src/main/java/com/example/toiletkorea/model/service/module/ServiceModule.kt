package com.example.toiletkorea.model.service.module

import android.app.Application
import android.content.Context
import com.example.toiletkorea.model.service.AccountService
import com.example.toiletkorea.model.service.impl.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    companion object{
        @Provides
        @Singleton
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }
    }
}
package com.example.cricut.di

import com.example.cricut.data.LocalRepository
import com.example.cricut.domain.GetDataUseCase
import com.example.cricut.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideRepository() : Repository {
        return LocalRepository()
    }

    @Singleton
    @Provides
    fun provideGetDataUseCase(repository: Repository) : GetDataUseCase {
        return GetDataUseCase(repository)
    }

}
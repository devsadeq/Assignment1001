package com.devsadeq.assignment1001.di

import com.devsadeq.assignment1001.data.remote.RemoteDataSourceImpl
import com.devsadeq.assignment1001.data.repository.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindRemoteDataSource(
        remoteDataSource: RemoteDataSourceImpl
    ): RemoteDataSource

}
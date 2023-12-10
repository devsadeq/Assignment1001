package com.devsadeq.assignment1001.di

import com.devsadeq.assignment1001.data.repository.ProductRepository
import com.devsadeq.assignment1001.data.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindProductsRepository(
        productsRepository: ProductRepositoryImpl
    ): ProductRepository

}
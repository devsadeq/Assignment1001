package com.devsadeq.assignment1001.di

import com.devsadeq.assignment1001.domain.usecase.ManageProductUseCase
import com.devsadeq.assignment1001.domain.usecase.ManageProductUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindManageProductUseCase(
        manageProduct: ManageProductUseCaseImpl
    ): ManageProductUseCase
}
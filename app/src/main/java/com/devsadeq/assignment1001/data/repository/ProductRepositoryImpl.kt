package com.devsadeq.assignment1001.data.repository

import com.devsadeq.assignment1001.data.repository.mapper.toEntity
import com.devsadeq.assignment1001.domain.model.Product
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return remoteDataSource.getProducts().toEntity()
    }
}
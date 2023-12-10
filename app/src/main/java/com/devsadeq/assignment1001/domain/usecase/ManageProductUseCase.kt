package com.devsadeq.assignment1001.domain.usecase

import com.devsadeq.assignment1001.data.repository.ProductRepository
import com.devsadeq.assignment1001.domain.model.Product
import javax.inject.Inject

interface ManageProductUseCase {
    suspend fun getProducts(): List<Product>
}

class ManageProductUseCaseImpl @Inject constructor(
    private val repository: ProductRepository
) : ManageProductUseCase {
    override suspend fun getProducts(): List<Product> {
        return repository.getProducts()
    }
}
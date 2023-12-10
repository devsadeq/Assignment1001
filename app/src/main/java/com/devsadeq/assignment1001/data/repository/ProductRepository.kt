package com.devsadeq.assignment1001.data.repository

import com.devsadeq.assignment1001.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
}
package com.devsadeq.assignment1001.data.repository

import com.devsadeq.assignment1001.data.remote.resources.ProductResource

interface RemoteDataSource {
    suspend fun getProducts(): List<ProductResource>
}
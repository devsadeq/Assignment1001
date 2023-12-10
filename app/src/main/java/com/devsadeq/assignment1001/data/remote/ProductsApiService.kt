package com.devsadeq.assignment1001.data.remote

import com.devsadeq.assignment1001.data.remote.resources.ProductResource
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApiService {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductResource>>
}
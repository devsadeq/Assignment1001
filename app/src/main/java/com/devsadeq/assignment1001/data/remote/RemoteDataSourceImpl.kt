package com.devsadeq.assignment1001.data.remote

import android.util.Log
import com.devsadeq.assignment1001.data.remote.resources.ProductResource
import com.devsadeq.assignment1001.data.repository.RemoteDataSource
import com.devsadeq.assignment1001.domain.model.NetworkException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val productsApiService: ProductsApiService
) : RemoteDataSource {
    override suspend fun getProducts(): List<ProductResource> {
        return tryToExecute { productsApiService.getProducts() }
    }

    private suspend fun <T> tryToExecute(func: suspend () -> Response<T>): T {
        val response = func()
        Log.d("TAG", "tryToExecute: ${response.code()}")
        if (response.isSuccessful) {
            return response.body() ?: throw NetworkException.NotFoundException
        }
        throw when (response.code()) {
            404 -> NetworkException.NotFoundException
            402 -> NetworkException.ApiKeyExpiredException
            401 -> NetworkException.UnAuthorizedException
            else -> IOException()
        }
    }
}
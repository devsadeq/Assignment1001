package com.devsadeq.assignment1001.domain.model

import com.devsadeq.assignment1001.data.remote.resources.RateResource


data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rate,
    val title: String
)



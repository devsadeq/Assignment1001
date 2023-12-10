package com.devsadeq.assignment1001.data.remote.resources


import com.google.gson.annotations.SerializedName

data class ProductResource(
    @SerializedName("category")
    val category: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("rating")
    val rating: RateResource?,
    @SerializedName("title")
    val title: String?
)
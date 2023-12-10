package com.devsadeq.assignment1001.ui.screen.products

import com.devsadeq.assignment1001.domain.model.Product

data class ProductsUIState(
    val isLoading: Boolean = true,
    val products: List<ProductItem> = emptyList(),
    val error: String? = null,
) {
    data class ProductItem(
        val category: String,
        val description: String,
        val id: Int,
        val image: String,
        val price: Double,
        val title: String,
        val rating: Double,
        val ratingCount: Int = 0,
    )
}

fun Product.toUIState() = ProductsUIState.ProductItem(
    category = category,
    description = description,
    id = id,
    image = image,
    price = price,
    title = title,
    rating = rating.rate,
    ratingCount = rating.count,
)

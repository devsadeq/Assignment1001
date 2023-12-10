package com.devsadeq.assignment1001.data.repository.mapper

import com.devsadeq.assignment1001.data.remote.resources.ProductResource
import com.devsadeq.assignment1001.data.remote.resources.RateResource
import com.devsadeq.assignment1001.domain.model.Product
import com.devsadeq.assignment1001.domain.model.Rate
import com.devsadeq.assignment1001.domain.util.orZero

fun ProductResource.toEntity() = Product(
    id = id.orZero(),
    title = title.orEmpty(),
    description = description.orEmpty(),
    image = image.orEmpty(),
    price = price.orZero(),
    category = category.orEmpty(),
    rating = rating.toEntity()
)

fun RateResource?.toEntity() = Rate(
    rate = this?.rate.orZero(),
    count = this?.count.orZero()
)

fun List<ProductResource>.toEntity() = map { it.toEntity() }
package com.devsadeq.assignment1001.ui.screen.products

import com.devsadeq.assignment1001.ui.base.BaseInteractionListener

interface ProductsInteractionListener : BaseInteractionListener {
    fun onProductClicked(productTitle: String)
}
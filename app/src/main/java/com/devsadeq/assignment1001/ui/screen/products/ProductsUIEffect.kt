package com.devsadeq.assignment1001.ui.screen.products

import com.devsadeq.assignment1001.ui.base.UIEffect

sealed interface ProductsUIEffect : UIEffect {
    data class ShowSnackBar(val message: String) : ProductsUIEffect
}
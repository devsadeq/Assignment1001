package com.devsadeq.assignment1001.ui.screen.products

import com.devsadeq.assignment1001.domain.model.Product
import com.devsadeq.assignment1001.domain.usecase.ManageProductUseCase
import com.devsadeq.assignment1001.ui.base.BaseViewModel
import com.devsadeq.assignment1001.ui.base.ErrorState
import com.devsadeq.assignment1001.ui.base.Stateful
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val manageProducts: ManageProductUseCase,
) : BaseViewModel<ProductsUIEffect>(),
    Stateful<ProductsUIState>, ProductsInteractionListener {
    override val uiState = MutableStateFlow(ProductsUIState())

    init {
        getProducts()
    }

    override fun onProductClicked(productTitle: String) {
        sendUiEffect(ProductsUIEffect.ShowSnackBar("Product $productTitle clicked"))
    }

    private fun getProducts() {
        tryToExecute(
            manageProducts::getProducts,
            ::onError,
            ::onGetProductsSuccess,
        )
    }

    private fun onGetProductsSuccess(products: List<Product>) {
        uiState.update {
            it.copy(
                products = products.map { product -> product.toUIState() },
                isLoading = false
            )
        }
    }

    private fun onError(error: ErrorState) {
        when (error) {
            is ErrorState.NotFound -> {
                stopLoading()
                sendUiEffect(ProductsUIEffect.ShowSnackBar("Network Error, Please try again"))
            }

            is ErrorState.NoInternet -> {
                stopLoading()
                sendUiEffect(ProductsUIEffect.ShowSnackBar("No Internet, Please check your connection"))
            }

            is ErrorState.Unknown -> {
                stopLoading()
                sendUiEffect(ProductsUIEffect.ShowSnackBar("Something went wrong, Please try again"))
            }
        }
    }

    private fun stopLoading() {
        uiState.update { it.copy(isLoading = false) }
    }

    private fun startLoading() {
        uiState.update { it.copy(isLoading = true) }
    }
}
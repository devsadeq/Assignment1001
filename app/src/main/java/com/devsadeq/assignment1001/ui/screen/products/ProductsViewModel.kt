package com.devsadeq.assignment1001.ui.screen.products

import com.devsadeq.assignment1001.domain.model.Product
import com.devsadeq.assignment1001.domain.usecase.ManageProductUseCase
import com.devsadeq.assignment1001.ui.base.BaseViewModel
import com.devsadeq.assignment1001.ui.base.ErrorState
import com.devsadeq.assignment1001.ui.base.Stateful
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
        sendUiEffect(ProductsUIEffect.ShowToast("Product $productTitle clicked"))
    }

    private fun getProducts() {
        tryToExecute(
            manageProducts::getProducts,
            ::onError,
            ::onGetProductsSuccess,
        )
    }

    private fun onGetProductsSuccess(products: List<Product>) {
        uiState.value = uiState.value.copy(
            products = products.map { it.toUIState() },
            isLoading = false
        )
    }

    private fun onError(error: ErrorState) {

    }
}
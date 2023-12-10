package com.devsadeq.assignment1001.ui.screen.products

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.devsadeq.assignment1001.ui.base.CollectUIEffect
import kotlinx.coroutines.coroutineScope

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    CollectUIEffect(uiEffect = viewModel.uiEffect) {
        when (it) {
            is ProductsUIEffect.ShowToast -> {
                showSnackbar(snackbarHostState, it.message)
            }
        }
    }
    ProductsScreenContent(state = state, interactionListener = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun ProductsScreenContent(
    state: ProductsUIState,
    interactionListener: ProductsInteractionListener,
    modifier: Modifier = Modifier,
) {
    Scaffold { paddingValues ->
        AnimatedContent(targetState = state.isLoading, label = "") {
            if (it) LoadingState()
            else ProductList(
                modifier = modifier.padding(paddingValues),
                products = state.products,
                interactionListener = interactionListener,
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductItem(
    product: ProductsUIState.ProductItem,
    interactionListener: ProductsInteractionListener,
    modifier: Modifier = Modifier,
) {
    ListItem(
        headlineText = { Text(text = product.title + " - ${product.rating}/5") },
        overlineText = { Text(text = product.category) },
        supportingText = { Text(text = product.description) },
        trailingContent = { Text(text = "$${product.price}") },
        modifier = Modifier.clickable { interactionListener.onProductClicked(product.title) },
        leadingContent = {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }
    )
}

@Composable
private fun ProductList(
    products: List<ProductsUIState.ProductItem>,
    interactionListener: ProductsInteractionListener,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(
            items = products
        ) { product ->
            ProductItem(
                product = product,
                interactionListener = interactionListener,
                modifier = Modifier
            )
        }
    }
}

private suspend fun showSnackbar(snackbarHostState: SnackbarHostState, message: String) {
    coroutineScope {
        snackbarHostState.showSnackbar(message)
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
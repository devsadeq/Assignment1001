## Project Overview

This Android app is built using Jetpack Compose, following the principles of Clean Architecture and MVVM design pattern. It aims to fetch and display a list of products from a public API and provide a clean, maintainable, and efficient codebase.

### Demo

![Demo](https://i.imgur.com/rKQWbw4.gif)

## Technology Stack

- **Jetpack Compose**: Modern Android UI toolkit for building native UIs.
- **AndroidX Libraries**: Core functionality for Android apps.
- **ViewModel**: Part of Android Architecture Components, it manages UI-related data.
- **Lifecycle**: Android Architecture Components that provides lifecycle-aware components.
- **Retrofit**: HTTP client for making network requests.
- **Gson**: Library for JSON serialization and deserialization.
- **Hilt**: Dependency injection library.
- **Coroutines**: Library for managing background threads.
- **Coil**: Image loading library for Android.

## Installation Guide

Follow these steps to set up and run the app:

1. Clone the repository:

   ```bash
   git clone https://github.com/devsadeq/assignment1001.git
2. Open the project in Android Studio.

3. Build and run the app on an emulator or physical device.


Public API Used
The app utilizes the public API available at https://fakestoreapi.com/products to fetch product data. For detailed API documentation, refer to https://fakestoreapi.com/docs.

## Implementation Details
### 1. Data Model
A data model Product is created to represent the product with fields for name, price, and image URL.
```kotlin
data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rate,
    val title: String
)
```
### 2. Network Request
An interface ProductApi is implemented to fetch a list of products from the provided API endpoint using Retrofit.

```kotlin
interface ProductsApiService {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductResource>>
}
```
### 3. Product Screen
A Compose UI screen is created to display the list of products. Each item in the list displays the product name, price, and an image.
```kotlin
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
```
### 4. Click Events
Click events on items are handled, and a Toast message with the product name is displayed when a user clicks on an item.
```kotlin
sealed interface ProductsUIEffect : UIEffect {
    data class ShowSnackBar(val message: String) : ProductsUIEffect
}
```

### 5. Error Handling
Potential error cases, such as network failures, are handled, and appropriate feedback is provided to the user.
```kotlin
    private fun runWithErrorCheck(
        onError: (ErrorState) -> Unit,
        scope: CoroutineScope = viewModelScope,
        dispatchers: CoroutineDispatcher = Dispatchers.IO,
        action: suspend () -> Unit,
    ): Job {
        return scope.launch(dispatchers) {
            try {
                action()
            } catch (e: NetworkException.NoInternetException) {
                onError(ErrorState.NoInternet)
            } catch (e: NetworkException.NotFoundException) {
                onError(ErrorState.NotFound)
            } catch (e: Exception) {
                onError(ErrorState.Unknown)
            }
        }
    }
```

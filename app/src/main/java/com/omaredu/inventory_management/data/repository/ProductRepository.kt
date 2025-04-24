package com.omaredu.inventory_management.data.repository

import com.omaredu.inventory_management.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductRepository {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    // Add some sample initial data
    init {
        _products.value = listOf(
            Product(
                name = "Laptop",
                description = "High-performance gaming laptop",
                price = 1299.99,
                quantity = 5
            ),
            Product(
                name = "Smartphone",
                description = "Latest model with advanced camera",
                price = 899.99,
                quantity = 10
            ),
            Product(
                name = "Headphones",
                description = "Noise-cancelling wireless headphones",
                price = 199.99,
                quantity = 15
            )
        )
    }

    fun addProduct(product: Product) {
        _products.update { currentList ->
            currentList + product
        }
    }

    fun updateProduct(product: Product) {
        _products.update { currentList ->
            currentList.map {
                if (it.id == product.id) product else it
            }
        }
    }

    fun deleteProduct(productId: String) {
        _products.update { currentList ->
            currentList.filter { it.id != productId }
        }
    }

    fun getProductById(productId: String): Product? {
        return products.value.find { it.id == productId }
    }

    companion object {
        // Singleton pattern for in-memory storage
        private var INSTANCE: ProductRepository? = null

        fun getInstance(): ProductRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = ProductRepository()
                INSTANCE = instance
                instance
            }
        }
    }
}
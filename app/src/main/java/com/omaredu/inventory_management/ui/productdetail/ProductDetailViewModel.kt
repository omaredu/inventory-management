package com.omaredu.inventory_management.ui.productdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omaredu.inventory_management.data.model.Product
import com.omaredu.inventory_management.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: ProductRepository = ProductRepository.getInstance()
) : ViewModel() {
    
    private val productId: String = checkNotNull(savedStateHandle["productId"])
    
    private val _uiState = MutableStateFlow<ProductDetailState>(ProductDetailState.Loading)
    val uiState: StateFlow<ProductDetailState> = _uiState.asStateFlow()
    
    init {
        loadProduct()
    }
    
    private fun loadProduct() {
        val product = repository.getProductById(productId)
        if (product != null) {
            _uiState.value = ProductDetailState.Success(product)
        } else {
            _uiState.value = ProductDetailState.Error("Product not found")
        }
    }
    
    fun incrementQuantity() {
        updateQuantity(1)
    }
    
    fun decrementQuantity() {
        updateQuantity(-1)
    }
    
    private fun updateQuantity(change: Int) {
        val currentState = _uiState.value
        if (currentState is ProductDetailState.Success) {
            val product = currentState.product
            val newQuantity = maxOf(0, product.quantity + change)
            
            if (newQuantity != product.quantity) {
                val updatedProduct = product.copy(quantity = newQuantity)
                repository.updateProduct(updatedProduct)
                _uiState.update { 
                    ProductDetailState.Success(updatedProduct)
                }
            }
        }
    }
}

sealed class ProductDetailState {
    object Loading : ProductDetailState()
    data class Success(val product: Product) : ProductDetailState()
    data class Error(val message: String) : ProductDetailState()
}
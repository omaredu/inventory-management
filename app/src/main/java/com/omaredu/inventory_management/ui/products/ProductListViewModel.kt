package com.omaredu.inventory_management.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omaredu.inventory_management.data.model.Product
import com.omaredu.inventory_management.data.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ProductListViewModel(
    private val repository: ProductRepository = ProductRepository.getInstance()
) : ViewModel() {
    
    val products: StateFlow<List<Product>> = repository.products
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    
    fun updateProductQuantity(productId: String, newQuantity: Int) {
        val product = repository.getProductById(productId)
        product?.let {
            repository.updateProduct(it.copy(quantity = newQuantity))
        }
    }
    
    fun deleteProduct(productId: String) {
        repository.deleteProduct(productId)
    }
}
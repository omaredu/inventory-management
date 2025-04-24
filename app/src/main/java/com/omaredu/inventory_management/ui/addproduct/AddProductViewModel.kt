package com.omaredu.inventory_management.ui.addproduct

import androidx.lifecycle.ViewModel
import com.omaredu.inventory_management.data.model.Product
import com.omaredu.inventory_management.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddProductViewModel(
    private val repository: ProductRepository = ProductRepository.getInstance()
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddProductState())
    val uiState: StateFlow<AddProductState> = _uiState.asStateFlow()
    
    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }
    
    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }
    
    fun updatePrice(price: String) {
        _uiState.update { 
            it.copy(
                price = price,
                priceError = if (price.isNotBlank()) validatePrice(price) else null
            )
        }
    }
    
    fun updateQuantity(quantity: String) {
        _uiState.update { 
            it.copy(
                quantity = quantity,
                quantityError = if (quantity.isNotBlank()) validateQuantity(quantity) else null
            )
        }
    }
    
    fun saveProduct(): Boolean {
        val currentState = uiState.value
        
        // Validate all fields
        val nameError = if (currentState.name.isBlank()) "Name cannot be empty" else null
        val descError = if (currentState.description.isBlank()) "Description cannot be empty" else null
        val priceError = validatePrice(currentState.price)
        val quantityError = validateQuantity(currentState.quantity)
        
        // Update state with errors
        _uiState.update {
            it.copy(
                nameError = nameError,
                descriptionError = descError,
                priceError = priceError,
                quantityError = quantityError,
                isSubmitting = true
            )
        }
        
        // Check if there are errors
        if (nameError != null || descError != null || priceError != null || quantityError != null) {
            _uiState.update { it.copy(isSubmitting = false) }
            return false
        }
        
        // Convert strings to appropriate types
        val priceValue = currentState.price.toDoubleOrNull() ?: 0.0
        val quantityValue = currentState.quantity.toIntOrNull() ?: 0
        
        // Create and save product
        val newProduct = Product(
            name = currentState.name,
            description = currentState.description,
            price = priceValue,
            quantity = quantityValue
        )
        
        repository.addProduct(newProduct)
        _uiState.update { AddProductState(isSubmitting = false) }
        return true
    }
    
    private fun validatePrice(price: String): String? {
        return when {
            price.isBlank() -> "Price cannot be empty"
            price.toDoubleOrNull() == null -> "Price must be a valid number"
            price.toDoubleOrNull()!! < 0 -> "Price cannot be negative"
            else -> null
        }
    }
    
    private fun validateQuantity(quantity: String): String? {
        return when {
            quantity.isBlank() -> "Quantity cannot be empty"
            quantity.toIntOrNull() == null -> "Quantity must be a whole number"
            quantity.toIntOrNull()!! < 0 -> "Quantity cannot be negative"
            else -> null
        }
    }
}

data class AddProductState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val quantity: String = "",
    val nameError: String? = null,
    val descriptionError: String? = null,
    val priceError: String? = null,
    val quantityError: String? = null,
    val isSubmitting: Boolean = false
)
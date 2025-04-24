package com.omaredu.inventory_management.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omaredu.inventory_management.data.model.Product
import com.omaredu.inventory_management.data.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DashboardViewModel(
    private val repository: ProductRepository = ProductRepository.getInstance()
) : ViewModel() {
    
    val dashboardState: StateFlow<DashboardState> = repository.products
        .map { products ->
            DashboardState(
                totalProducts = products.size,
                totalItems = products.sumOf { it.quantity },
                lowStockCount = products.count { it.quantity < LOW_STOCK_THRESHOLD },
                totalValue = products.sumOf { it.price * it.quantity }
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            DashboardState()
        )

    companion object {
        private const val LOW_STOCK_THRESHOLD = 5
    }
}

data class DashboardState(
    val totalProducts: Int = 0,
    val totalItems: Int = 0,
    val lowStockCount: Int = 0,
    val totalValue: Double = 0.0
)
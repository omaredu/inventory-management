package com.omaredu.inventory_management.ui.products

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.omaredu.inventory_management.data.model.Product
import com.omaredu.inventory_management.R
import androidx.compose.ui.res.stringResource
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = viewModel(),
    onNavigateToAddProduct: () -> Unit,
    onNavigateToProductDetail: (String) -> Unit,
    onNavigateBack: () -> Unit = {}
) {
    val products by viewModel.products.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.product_list)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(id = R.string.back))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddProduct) {
                Icon(Icons.Default.Add, contentDescription = stringResource(id = R.string.add_product))
            }
        }
    ) { padding ->
        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_products),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        onProductClick = { onNavigateToProductDetail(product.id) },
                        onDecreaseQuantity = {
                            if (product.quantity > 0) {
                                viewModel.updateProductQuantity(product.id, product.quantity - 1)
                            }
                        },
                        onIncreaseQuantity = {
                            viewModel.updateProductQuantity(product.id, product.quantity + 1)
                        },
                        onDeleteProduct = {
                            viewModel.deleteProduct(product.id)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(
    product: Product,
    onProductClick: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDeleteProduct: () -> Unit
) {
    Card(
        onClick = onProductClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = formatCurrency(product.price),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                
                IconButton(onClick = onDeleteProduct) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.quantity),
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                IconButton(
                    onClick = onDecreaseQuantity,
                    enabled = product.quantity > 0
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = stringResource(id = R.string.decrease)
                    )
                }
                
                Text(
                    text = product.quantity.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (product.quantity < 5) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
                
                IconButton(onClick = onIncreaseQuantity) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = stringResource(id = R.string.increase)
                    )
                }
            }
        }
    }
}

private fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
    return format.format(amount)
}
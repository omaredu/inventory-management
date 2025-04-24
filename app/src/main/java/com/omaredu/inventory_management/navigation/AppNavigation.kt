package com.omaredu.inventory_management.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.omaredu.inventory_management.ui.addproduct.AddProductScreen
import com.omaredu.inventory_management.ui.dashboard.DashboardScreen
import com.omaredu.inventory_management.ui.productdetail.ProductDetailScreen
import com.omaredu.inventory_management.ui.products.ProductListScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToProductList = {
                    navController.navigate(Screen.ProductList.route)
                }
            )
        }
        
        composable(Screen.ProductList.route) {
            ProductListScreen(
                onNavigateToAddProduct = {
                    navController.navigate(Screen.AddProduct.route)
                },
                onNavigateToProductDetail = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.StringType
                }
            )
        ) {
            ProductDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.AddProduct.route) {
            AddProductScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onProductAdded = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object ProductList : Screen("product_list")
    object AddProduct : Screen("add_product")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
}
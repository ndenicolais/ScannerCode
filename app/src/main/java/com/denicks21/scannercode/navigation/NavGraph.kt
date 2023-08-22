package com.denicks21.scannercode.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.denicks21.scannercode.scanner.ScannerPage

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = NavScreens.ScannerPage.route
    ) {
        composable(route = NavScreens.ScannerPage.route) {
            ScannerPage()
        }
    }
}
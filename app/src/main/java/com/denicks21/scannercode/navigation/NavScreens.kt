package com.denicks21.scannercode.navigation

sealed class NavScreens(val route: String) {
    object ScannerPage : NavScreens("Scanner")
}
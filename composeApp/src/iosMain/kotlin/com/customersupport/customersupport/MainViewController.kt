package com.customersupport.customersupport

import androidx.compose.ui.window.ComposeUIViewController
import com.customersupport.customersupport.database.DatabaseDriverFactory
import com.customersupport.customersupport.database.PlatformContext

fun MainViewController() = ComposeUIViewController {
    App(DatabaseDriverFactory(PlatformContext()))
}
package com.customersupport.customersupport.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class PlatformContext

actual class DatabaseDriverFactory actual constructor(appContext: PlatformContext) {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, "customer_support.db")
    }
}
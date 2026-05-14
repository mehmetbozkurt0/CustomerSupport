package com.customersupport.customersupport.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.customersupport.customersupport.database.AppDatabase

actual class PlatformContext(val androidContext: Context)

actual class DatabaseDriverFactory actual constructor(appContext: PlatformContext) {
    private val context = appContext.androidContext
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "customer_support.db")
    }
}
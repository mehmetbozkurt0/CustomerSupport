package com.customersupport.customersupport.database

import app.cash.sqldelight.db.SqlDriver

expect class PlatformContext
expect class DatabaseDriverFactory(appContext: PlatformContext) {
    fun createDriver(): SqlDriver
}
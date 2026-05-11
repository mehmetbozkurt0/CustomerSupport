package com.customersupport.customersupport

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
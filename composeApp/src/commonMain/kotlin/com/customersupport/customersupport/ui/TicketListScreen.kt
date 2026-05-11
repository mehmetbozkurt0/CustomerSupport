package com.customersupport.customersupport.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TicketListScreen(onTicketClick: () -> Unit, onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Destek Taleplerim", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onTicketClick) {
            Text("Örnek Bilete Git (Sohbet)")
        }
        Button(onClick = onLogout) {
            Text("Çıkış Yap")
        }
    }
}
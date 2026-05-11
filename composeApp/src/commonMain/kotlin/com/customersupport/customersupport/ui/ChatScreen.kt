package com.customersupport.customersupport.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ChatScreen(onBack: () -> Unit, onVideoCall: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Canlı Destek (Sohbet)", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onVideoCall) {
            Text("Görüntülü Görüşme Başlat")
        }
        Button(onClick = onBack) {
            Text("Geri Dön")
        }
    }
}
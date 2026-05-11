package com.customersupport.customersupport.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun VideoCallScreen(onEndCall: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Görüntülü Görüşme", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onEndCall) {
            Text("Çağrıyı Sonlandır")
        }
    }
}
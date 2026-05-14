package com.customersupport.customersupport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import customersupport.composeapp.generated.resources.Res
import customersupport.composeapp.generated.resources.ic_call_end
import customersupport.composeapp.generated.resources.ic_cam
import customersupport.composeapp.generated.resources.ic_cam_off
import customersupport.composeapp.generated.resources.ic_mic
import customersupport.composeapp.generated.resources.ic_mic_off

@Composable
fun VideoCallScreen(onEndCall: () -> Unit) {
    val darkBackground = Color(0xFF121212)
    val surfaceDark = Color(0xFF2C2C2E)
    val endCallRed = Color(0xFFE53935)
    val primaryBlue = Color(0xFF0047AB)

    var isMuted by remember { mutableStateOf(false) }
    var isCameraOff by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        // Arkaplan
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF1E293B), Color(0xFF0F172A)))),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(primaryBlue.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("S", color = primaryBlue, fontSize = 56.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Selin Y.", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("02:14", color = Color.White.copy(alpha = 0.6f), fontSize = 16.sp)
            }
        }

        // PIP Kamera
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 56.dp, end = 20.dp)
                .size(width = 110.dp, height = 160.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.DarkGray)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = if (isCameraOff) "Kamera Kapalı" else "Sen",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Kontrol Paneli
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Mikrofon Butonu
            IconButton(
                onClick = { isMuted = !isMuted },
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(if (isMuted) Color.White.copy(alpha = 0.8f) else surfaceDark)
            ) {
                Icon(
                    painter = painterResource(if (isMuted) Res.drawable.ic_mic_off else Res.drawable.ic_mic),
                    contentDescription = "Mikrofon",
                    tint = if (isMuted) Color.Black else Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }


            //Çağrıyı Sonlandır Butonu
            IconButton(
                onClick = onEndCall,
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(endCallRed)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_call_end),
                    contentDescription = "Çağrıyı Sonlandır",
                    tint = Color.White,
                    modifier = Modifier.size(34.dp)
                )
            }

            //Kamera Kapatma Butonu
            IconButton(
                onClick = { isCameraOff = !isCameraOff },
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(if (isCameraOff) Color.White.copy(alpha = 0.8f) else surfaceDark)
            ) {
                Icon(
                    painter = painterResource(if (isCameraOff) Res.drawable.ic_cam_off else Res.drawable.ic_cam),
                    contentDescription = "Kamera",
                    tint = if (isCameraOff) Color.Black else Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
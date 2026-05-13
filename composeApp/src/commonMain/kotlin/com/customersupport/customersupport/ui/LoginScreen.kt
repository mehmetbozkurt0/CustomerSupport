package com.customersupport.customersupport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val primaryDark = Color(0xFF002255)
    val primaryBlue = Color(0xFF0047AB)
    val backgroundColor = Color(0xFFF8F9FB)
    val surfaceColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-100).dp, y = (-50).dp)
                .alpha(0.3f)
                .background(Brush.radialGradient(listOf(primaryBlue, Color.Transparent)), CircleShape)
        )

        Box(
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 150.dp, y = 100.dp)
                .alpha(0.2f)
                .background(Brush.radialGradient(listOf(primaryDark, Color.Transparent)), CircleShape)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(85.dp)
                    .shadow(elevation = 20.dp, shape = RoundedCornerShape(26.dp), spotColor = primaryBlue)
                    .clip(RoundedCornerShape(26.dp))
                    .background(Brush.linearGradient(listOf(primaryBlue, primaryDark))),
                contentAlignment = Alignment.Center
            ) {
                Text("🛡️", fontSize = 42.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Hoş Geldiniz",
                fontSize = 34.sp,
                fontWeight = FontWeight.ExtraBold,
                color = primaryDark,
                letterSpacing = (-1).sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Güvenli destek paneline erişmek için lütfen giriş yapın.",
                fontSize = 15.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.alpha(0.8f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("E-posta veya kullanıcı adı", color = Color.LightGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp), spotColor = Color.LightGray),
                shape = RoundedCornerShape(20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = surfaceColor,
                    unfocusedContainerColor = surfaceColor,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Şifreniz", color = Color.LightGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp), spotColor = Color.LightGray),
                shape = RoundedCornerShape(20.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = surfaceColor,
                    unfocusedContainerColor = surfaceColor,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                TextButton(onClick = { /* Şifre sıfırlama */ }) {
                    Text("Yardım mı lazım?", color = primaryBlue, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        isError = false
                        onLoginSuccess()
                    } else {
                        isError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(elevation = 15.dp, shape = RoundedCornerShape(30.dp), spotColor = primaryBlue),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.horizontalGradient(listOf(primaryBlue, primaryDark))),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Giriş Yap", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            if (isError) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Lütfen bilgilerinizi kontrol edin.", color = Color(0xFFD32F2F), fontSize = 14.sp)
            }
        }
    }
}
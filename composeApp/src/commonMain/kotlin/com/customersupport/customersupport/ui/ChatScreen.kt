package com.customersupport.customersupport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val time: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(onBack: () -> Unit, onVideoCall: () -> Unit) {
    val primaryDark = Color(0xFF002255)
    val primaryBlue = Color(0xFF0047AB)
    val backgroundColor = Color(0xFFF8F9FB)
    val surfaceColor = Color.White

    var messageText by remember { mutableStateOf("") }

    val mockMessages = listOf(
        ChatMessage("1", "Merhaba! Destek ekibine ulaştığınız için teşekkürler. #10482 numaralı biletiniz hakkında size nasıl yardımcı olabilirim?", false, "10:42"),
        ChatMessage("2", "Evet, yeni kredi kartımla fatura bilgilerimi güncellemeye çalıştım ancak sistem sürekli hata veriyor. Acaba sorun nedir?", true, "10:44"),
        ChatMessage("3", "Bunu sizin için hemen kontrol ediyorum. Sistem loglarına bakmam için lütfen kısa bir saniye bekleyin.", false, "10:45")
    )

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            Surface(
                color = surfaceColor,
                modifier = Modifier.shadow(elevation = 4.dp, spotColor = Color.LightGray)
            ) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(primaryBlue.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("S", color = primaryBlue, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(verticalArrangement = Arrangement.Center) {
                                Text(
                                    text = "Selin Y.",
                                    fontWeight = FontWeight.Bold,
                                    color = primaryDark,
                                    fontSize = 16.sp,
                                    modifier = Modifier.offset(y = (5).dp)
                                )
                                Text(
                                    text = "Destek Uzmanı",
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    modifier = Modifier.offset(y = (-1).dp)
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        TextButton(onClick = onBack, contentPadding = PaddingValues(horizontal = 8.dp)) {
                            Text("‹ Geri", color = primaryBlue, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = onVideoCall,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(38.dp)
                                .background(primaryBlue.copy(alpha = 0.1f), CircleShape)
                        ) {
                            Text("📞", fontSize = 16.sp)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = surfaceColor)
                )
            }
        },
        bottomBar = {
            Surface(
                color = surfaceColor,
                modifier = Modifier.shadow(elevation = 16.dp, spotColor = Color.LightGray)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                        .navigationBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { /* Dosya ekleme eklenecek */ },
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Text("+", fontSize = 28.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        placeholder = { Text("Bir mesaj yazın...", color = Color.LightGray, fontSize = 15.sp) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = backgroundColor,
                            unfocusedContainerColor = backgroundColor,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                messageText = ""
                            }
                        },
                        modifier = Modifier
                            .background(primaryBlue, CircleShape)
                            .size(44.dp)
                    ) {
                        Text("↑", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Bugün, 10:42",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .background(Color(0xFFEBEBEB), RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            items(mockMessages) { message ->
                MessageBubble(message = message, primaryBlue = primaryBlue)
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage, primaryBlue: Color) {
    val isUser = message.isFromUser

    val backgroundColor = if (isUser) primaryBlue else Color.White
    val textColor = if (isUser) Color.White else Color(0xFF333333)
    val timeColor = if (isUser) Color.White.copy(alpha = 0.7f) else Color.Gray

    val bubbleShape = if (isUser) {
        RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 4.dp)
    } else {
        RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 4.dp, bottomEnd = 20.dp)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .shadow(if (isUser) 4.dp else 2.dp, bubbleShape, spotColor = if (isUser) primaryBlue else Color.LightGray)
                .clip(bubbleShape)
                .background(backgroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column {
                Text(
                    text = message.text,
                    color = textColor,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.time,
                    color = timeColor,
                    fontSize = 11.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
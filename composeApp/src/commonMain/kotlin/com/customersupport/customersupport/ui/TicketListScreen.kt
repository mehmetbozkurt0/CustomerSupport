package com.customersupport.customersupport.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListScreen(viewModel: TicketViewModel, onTicketClick: () -> Unit, onLogout: () -> Unit) {
    val primaryDark = Color(0xFF002255)
    val primaryBlue = Color(0xFF0047AB)
    val backgroundColor = Color(0xFFF8F9FB)
    val subtleGray = Color(0xFF75777E)

    LaunchedEffect(Unit) {
        viewModel.fetchTickets()
    }

    val tickets by viewModel.tickets.collectAsState()
    val isLoading by viewModel.isLoadingTickets.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Box(
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = (-50).dp)
                .alpha(0.45f)
                .background(Brush.radialGradient(listOf(primaryBlue, Color.Transparent)), CircleShape)
        )

        Box(
            modifier = Modifier
                .size(500.dp)
                .align(Alignment.CenterStart)
                .offset(x = (-150).dp, y = 100.dp)
                .alpha(0.35f)
                .background(Brush.radialGradient(listOf(primaryDark, Color.Transparent)), CircleShape)
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text("Taleplerim", fontWeight = FontWeight.ExtraBold, color = primaryDark, fontSize = 24.sp)
                    },
                    navigationIcon = {
                        Box(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 8.dp)
                                .size(40.dp)
                                .shadow(4.dp, CircleShape, spotColor = primaryBlue)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(primaryBlue, primaryDark))),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("MB", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                    },
                    actions = {
                        OutlinedButton(
                            onClick = onLogout,
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .height(36.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.White.copy(alpha = 0.90f),
                                contentColor = primaryDark
                            ),
                            border = BorderStroke(1.5.dp, Color.White),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp)
                        ) {
                            Text("Çıkış", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /* Yeni talep */ },
                    containerColor = primaryBlue,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.shadow(12.dp, RoundedCornerShape(20.dp), spotColor = primaryBlue)
                ) {
                    Text("+", fontSize = 28.sp, fontWeight = FontWeight.Medium)
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Aktif ve çözümlenmiş destek taleplerinizi yönetin.",
                    color = subtleGray,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                if (isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = primaryBlue)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Talepleriniz güncelleniyor...", color = subtleGray, fontSize = 15.sp)
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 90.dp)
                    ) {
                        items(tickets) { ticket ->
                            TicketCard(ticket = ticket, onClick = onTicketClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TicketCard(ticket: UiTicket, onClick: () -> Unit) {
    val statusColor = when (ticket.status) {
        "Açık" -> Color(0xFF0047AB)
        "Beklemede" -> Color(0xFFF57C00)
        "Çözüldü" -> Color(0xFF388E3C)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, Color.White.copy(alpha = 0.9f), RoundedCornerShape(24.dp))
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp), spotColor = Color(0xFF002255).copy(alpha = 0.15f))
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.65f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${ticket.id} • ${ticket.date}",
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = ticket.status,
                        color = statusColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = ticket.title,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF002255)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = ticket.lastMessage,
                fontSize = 14.sp,
                color = Color(0xFF333333),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 22.sp
            )
        }
    }
}
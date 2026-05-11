package com.customersupport.customersupport

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.customersupport.customersupport.ui.ChatScreen
import com.customersupport.customersupport.ui.LoginScreen
import com.customersupport.customersupport.ui.TicketListScreen
import com.customersupport.customersupport.ui.VideoCallScreen

enum class Screen { LOGIN, TICKET_LIST, CHAT, VIDEO_CALL }

@Composable
fun App() {
    MaterialTheme {
        var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

        when (currentScreen) {
            Screen.LOGIN -> LoginScreen(
                onLoginSuccess = { currentScreen = Screen.TICKET_LIST }
            )
            Screen.TICKET_LIST -> TicketListScreen(
                onTicketClick = { currentScreen = Screen.CHAT },
                onLogout = { currentScreen = Screen.LOGIN }
            )
            Screen.CHAT -> ChatScreen(
                onBack = { currentScreen = Screen.TICKET_LIST },
                onVideoCall = { currentScreen = Screen.VIDEO_CALL }
            )
            Screen.VIDEO_CALL -> VideoCallScreen(
                onEndCall = { currentScreen = Screen.CHAT }
            )
        }
    }
}
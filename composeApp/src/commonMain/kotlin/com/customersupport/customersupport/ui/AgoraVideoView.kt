package com.customersupport.customersupport.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

expect @Composable fun AgoraVideoView(
    modifier: Modifier,
    channelName: String,
    isMuted: Boolean,
    isCameraOff: Boolean,
    onCallEnded: () -> Unit
)
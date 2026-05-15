package com.customersupport.customersupport.ui

import android.Manifest
import android.view.SurfaceView
import android.view.TextureView
import android.widget.FrameLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.video.VideoCanvas
import io.agora.rtc2.video.VideoEncoderConfiguration

actual @Composable fun AgoraVideoView(
    modifier: Modifier,
    channelName: String,
    isMuted: Boolean,
    isCameraOff: Boolean,
    onCallEnded: () -> Unit
) {
    val context = LocalContext.current
    var rtcEngine by remember { mutableStateOf<RtcEngine?>(null) }
    var permissionsGranted by remember { mutableStateOf(false) }
    var initError by remember { mutableStateOf("") }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            permissionsGranted = permissions[Manifest.permission.CAMERA] == true &&
                    permissions[Manifest.permission.RECORD_AUDIO] == true
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
        )
    }

    if (permissionsGranted) {
        DisposableEffect(Unit) {
            var engine: RtcEngine? = null
            try {
                val config = RtcEngineConfig()
                config.mContext = context
                config.mAppId = "3062cebc20e240f88a76f4dae65d64a4"
                config.mEventHandler = object : IRtcEngineEventHandler() {
                    override fun onLeaveChannel(stats: RtcStats?) {
                        onCallEnded()
                    }
                }

                engine = RtcEngine.create(config)
                engine.enableVideo()

                engine.setVideoEncoderConfiguration(
                    VideoEncoderConfiguration(
                        VideoEncoderConfiguration.VD_640x360,
                        VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_30,
                        VideoEncoderConfiguration.STANDARD_BITRATE,
                        VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT
                    )
                )

                engine.joinChannel(null, channelName, "", 0)
                rtcEngine = engine
            } catch (e: Exception) {
                initError = "Agora başlatılamadı."
            }

            onDispose {
                engine?.leaveChannel()
                RtcEngine.destroy()
            }
        }

        LaunchedEffect(isMuted, rtcEngine) {
            rtcEngine?.muteLocalAudioStream(isMuted)
        }

        LaunchedEffect(isCameraOff, rtcEngine) {
            rtcEngine?.muteLocalVideoStream(isCameraOff)
            rtcEngine?.enableLocalVideo(!isCameraOff)
            if (!isCameraOff) {
                rtcEngine?.startPreview()
            }
        }

        if (initError.isNotEmpty()) {
            Box(modifier = modifier.background(Color.DarkGray), contentAlignment = Alignment.Center) {
                Text(initError, color = Color.Red, textAlign = TextAlign.Center)
            }
        } else if (rtcEngine != null) {
            AndroidView(
                factory = { ctx ->
                    FrameLayout(ctx).apply {
                        val textureView = TextureView(ctx)
                        addView(textureView)
                        val canvas = VideoCanvas(textureView, VideoCanvas.RENDER_MODE_HIDDEN, 0)
                        rtcEngine?.setupLocalVideo(canvas)
                        rtcEngine?.startPreview()
                    }
                },
                modifier = modifier
            )
        }
    } else {
        Box(modifier = modifier.background(Color.DarkGray), contentAlignment = Alignment.Center) {
            Text("Kamera izni bekleniyor...", color = Color.White, textAlign = TextAlign.Center)
        }
    }
}
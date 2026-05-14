package com.customersupport.customersupport.ui

import android.view.SurfaceView
import android.widget.FrameLayout
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.video.VideoCanvas

actual @Composable fun AgoraVideoView(
    modifier: Modifier,
    channelName: String,
    onCallEnded: () -> Unit
) {
    val context = LocalContext.current
    var rtcEngine by remember { mutableStateOf<RtcEngine?>(null) }
    var localSurfaceView by remember { mutableStateOf<SurfaceView?>(null) }

    DisposableEffect(Unit) {
        val config = RtcEngineConfig()
        config.mContext = context
        config.mAppId = "TEST_APP_ID_BURAYA_GELECEK"
        config.mEventHandler = object : IRtcEngineEventHandler() {
            override fun onLeaveChannel(stats: RtcStats?) {
                onCallEnded()
            }
        }

        val engine = RtcEngine.create(config)
        engine.enableVideo()

        val surface = SurfaceView(context)
        surface.setZOrderMediaOverlay(true)

        engine.setupLocalVideo(VideoCanvas(surface, VideoCanvas.RENDER_MODE_HIDDEN, 0))
        engine.startPreview()

        engine.joinChannel(null, channelName, "", 0)

        localSurfaceView = surface
        rtcEngine = engine

        onDispose {
            engine.leaveChannel()
            RtcEngine.destroy()
        }
    }

    if (localSurfaceView != null) {
        AndroidView(
            factory = {
                FrameLayout(context).apply {
                    addView(localSurfaceView)
                }
            },
            modifier = modifier
        )
    }
}
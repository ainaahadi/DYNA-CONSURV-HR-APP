package com.example.hrapp.presentation.screens.attendance

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun QRScannerScreen(onBack: () -> Unit) {
    var isSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Simulate auto scan success for prototype
        delay(4000)
        isSuccess = true
        delay(1500)
        onBack()
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Mock Camera Background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray.copy(alpha = 0.5f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {
                    Icon(Icons.Default.FlashOn, contentDescription = "Flash", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            ScannerFrame()

            Text(
                "Align QR code within frame",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 24.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { 
                    isSuccess = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.1f),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
            ) {
                Text("ENTER CODE MANUALLY", fontWeight = FontWeight.Bold)
            }
        }

        // Success Overlay
        AnimatedVisibility(
            visible = isSuccess,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF50C878)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Success!", style = MaterialTheme.typography.headlineLarge, color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Attendance Marked", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ScannerFrame() {
    val infiniteTransition = rememberInfiniteTransition()
    val scanLineY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 250f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.size(250.dp).graphicsLayer(scaleX = scale, scaleY = scale)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 4.dp.toPx()
            val cornerLength = 40.dp.toPx()
            val color = Color(0xFF4A90E2)

            // Top Left
            drawPath(
                path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, cornerLength)
                    lineTo(0f, 0f)
                    lineTo(cornerLength, 0f)
                },
                color = color,
                style = Stroke(width = strokeWidth)
            )

            // Top Right
            drawPath(
                path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(size.width - cornerLength, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, cornerLength)
                },
                color = color,
                style = Stroke(width = strokeWidth)
            )

            // Bottom Left
            drawPath(
                path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, size.height - cornerLength)
                    lineTo(0f, size.height)
                    lineTo(cornerLength, size.height)
                },
                color = color,
                style = Stroke(width = strokeWidth)
            )

            // Bottom Right
            drawPath(
                path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(size.width - cornerLength, size.height)
                    lineTo(size.width, size.height)
                    lineTo(size.width, size.height - cornerLength)
                },
                color = color,
                style = Stroke(width = strokeWidth)
            )

            // Scan Line
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(0f, scanLineY),
                end = androidx.compose.ui.geometry.Offset(size.width, scanLineY),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}

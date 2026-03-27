package com.example.hrapp.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hrapp.composeapp.generated.resources.Res
import hrapp.composeapp.generated.resources.hrapp_logo
import org.jetbrains.compose.resources.painterResource

import com.example.hrapp.theme.ThemeState

// From the design PDF - HRApps branding colors
private val BrandPrimary = Color(0xFF1A3C8F)      // Deep Navy Blue
private val BrandAccent  = Color(0xFFD32F2F)      // Crimson Red (man figure in logo)
private val GradStart    = Color(0xFF7B1FA2)      // Purple top
private val GradMid      = Color(0xFF3B2A6B)      // Dark purple mid
private val GradEnd      = Color(0xFF1565C0)      // Deep blue bottom

// Dark Mode palette overrides
private val DarkGradStart = Color(0xFF3E1051)     // Deeper purple
private val DarkGradMid   = Color(0xFF1D1537)     // Very dark purple
private val DarkGradEnd   = Color(0xFF0C386A)     // Very dark blue

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val isDark = ThemeState.isDarkModeEnabled ?: androidx.compose.foundation.isSystemInDarkTheme()
    val gradientColors = if (isDark) listOf(DarkGradStart, DarkGradMid, DarkGradEnd) else listOf(GradStart, GradMid, GradEnd)
    val crestBg = if (isDark) MaterialTheme.colorScheme.surface else Color.White
    val welcomeText = if (isDark) Color.LightGray else Color(0xFF555555)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ─── White curved top section with logo ───
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.42f)
                    .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                    .background(crestBg),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "Welcome to",
                        style = MaterialTheme.typography.bodyLarge,
                        color = welcomeText,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Image(
                        painter = painterResource(Res.drawable.hrapp_logo),
                        contentDescription = "HR Apps Logo",
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .height(90.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            // ─── Login form ───
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Email field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email", color = Color.White.copy(alpha = 0.7f)) },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White.copy(alpha = 0.7f))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedBorderColor = Color.White,
                        unfocusedContainerColor = Color.White.copy(alpha = 0.15f),
                        focusedContainerColor = Color.White.copy(alpha = 0.20f),
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password", color = Color.White.copy(alpha = 0.7f)) },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White.copy(alpha = 0.7f))
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedBorderColor = Color.White,
                        unfocusedContainerColor = Color.White.copy(alpha = 0.15f),
                        focusedContainerColor = Color.White.copy(alpha = 0.20f),
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Forgot Password
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Forgot your password?",
                        color = Color.White.copy(alpha = 0.8f),
                        textDecoration = TextDecoration.Underline,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable { }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Login button
                Button(
                    onClick = onLoginSuccess,
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = BrandPrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text(
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

    val footerBg = crestBg
    val footerText = if (isDark) Color.LightGray else Color.DarkGray
    val dynaText = if (isDark) Color(0xFFEF5350) else Color(0xFFD32F2F)
    val consurvText = if (isDark) Color(0xFF64B5F6) else Color(0xFF1A3C8F)

        // ─── © Footer pinned to bottom ───
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(footerBg)
                .padding(vertical = 10.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = footerText, fontSize = 12.sp)) { append("© 2024 Powered by ") }
                    withStyle(SpanStyle(color = dynaText, fontSize = 12.sp, fontWeight = FontWeight.Bold)) { append("Dyna") }
                    withStyle(SpanStyle(color = consurvText, fontSize = 12.sp, fontWeight = FontWeight.Bold)) { append("Consurv") }
                },
                textAlign = TextAlign.Center
            )
        }
    }
}

package com.example.hrapp.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.hrapp.theme.ThemeState
import hrapp.composeapp.generated.resources.Res
import hrapp.composeapp.generated.resources.hrapp_logo
import org.jetbrains.compose.resources.painterResource

private val LoginTopGradient = Color(0xFF980D43)
private val LoginMidGradient = Color(0xFF61417B)
private val LoginBottomGradient = Color(0xFF36A3ED)
private val LoginPanel = Color(0xFFF6F8FD)
private val LoginText = Color(0xFF2A2F3E)
private val LoginMuted = Color(0xFF82899A)
private val LoginPrimary = Color(0xFF2345A2)
private val LoginAccent = Color(0xFFD43D50)

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showPasswordHelp by remember { mutableStateOf(false) }

    val isDark = ThemeState.isDarkModeEnabled ?: androidx.compose.foundation.isSystemInDarkTheme()
    val gradient = if (isDark) {
        listOf(Color(0xFF671031), Color(0xFF2B2148), Color(0xFF0E4D84))
    } else {
        listOf(LoginTopGradient, LoginMidGradient, LoginBottomGradient)
    }
    val topPanel = if (isDark) MaterialTheme.colorScheme.surface else LoginPanel
    val fieldContainer = if (isDark) {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.94f)
    } else {
        Color.White.copy(alpha = 0.96f)
    }
    val contentText = if (isDark) MaterialTheme.colorScheme.onSurface else LoginText

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradient))
    ) {
        DecorativeOrb(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = (-84).dp, y = 96.dp)
                .size(260.dp),
            alpha = 0.14f
        )
        DecorativeOrb(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = 48.dp, y = 12.dp)
                .size(214.dp),
            alpha = 0.10f
        )
        DecorativeOrb(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 90.dp, y = (-50).dp)
                .size(300.dp),
            alpha = 0.12f
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.05f)
                    .clip(RoundedCornerShape(bottomStart = 140.dp, bottomEnd = 140.dp))
                    .background(topPanel),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 460.dp)
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome to",
                        color = contentText.copy(alpha = 0.84f),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Image(
                        painter = painterResource(Res.drawable.hrapp_logo),
                        contentDescription = "HR Apps Logo",
                        modifier = Modifier
                            .fillMaxWidth(0.86f)
                            .height(116.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .widthIn(max = 460.dp)
                    .padding(horizontal = 30.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Email",
                    keyboardType = KeyboardType.Email,
                    containerColor = fieldContainer,
                    textColor = LoginText
                )

                Spacer(modifier = Modifier.height(14.dp))

                LoginField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Password",
                    keyboardType = KeyboardType.Password,
                    containerColor = fieldContainer,
                    textColor = LoginText,
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                tint = LoginMuted
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Forgot your password?",
                        color = Color.White.copy(alpha = 0.84f),
                        textDecoration = TextDecoration.Underline,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable { showPasswordHelp = !showPasswordHelp }
                    )
                }

                if (showPasswordHelp) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Use your company email or contact HR support to reset your password.",
                        color = Color.White.copy(alpha = 0.84f),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = onLoginSuccess,
                    modifier = Modifier
                        .fillMaxWidth(0.72f)
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = LoginText
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text(
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(topPanel)
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = contentText.copy(alpha = 0.82f), fontSize = 12.sp)) {
                            append("(c) 2024 Powered by ")
                        }
                        withStyle(SpanStyle(color = LoginAccent, fontWeight = FontWeight.Bold, fontSize = 12.sp)) {
                            append("Dyna")
                        }
                        withStyle(SpanStyle(color = LoginPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)) {
                            append("Consurv")
                        }
                    },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun LoginField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType,
    containerColor: Color,
    textColor: Color,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                color = LoginMuted
            )
        },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = LoginPrimary,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor
        )
    )
}

@Composable
private fun DecorativeOrb(
    modifier: Modifier,
    alpha: Float
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White.copy(alpha = alpha))
    )
}

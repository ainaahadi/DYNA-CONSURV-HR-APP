package com.example.hrapp.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hrapp.theme.ThemeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkMode = ThemeState.isDarkModeEnabled ?: systemIsDark
    var pushNotificationsEnabled by remember { mutableStateOf(true) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var compactLayoutEnabled by remember { mutableStateOf(false) }
    var twoFactorEnabled by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf<String?>(null) }
    var dialogMessage by remember { mutableStateOf("") }
    var statusMessage by remember {
        mutableStateOf("Prototype preferences are saved locally while you test the app UI.")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatusCard(message = statusMessage)

            SettingsSectionHeader("Appearance")
            SettingsCard {
                SettingsSwitchRow(
                    icon = Icons.Default.DarkMode,
                    iconTint = Color(0xFF5C6BC0),
                    label = "Dark Mode",
                    description = "Switch between light and dark theme",
                    checked = isDarkMode,
                    onCheckedChange = { ThemeState.isDarkModeEnabled = it }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                SettingsSwitchRow(
                    icon = Icons.Default.Notifications,
                    iconTint = Color(0xFFE53935),
                    label = "Push Notifications",
                    description = "Get alerted for new announcements",
                    checked = pushNotificationsEnabled,
                    onCheckedChange = {
                        pushNotificationsEnabled = it
                        statusMessage = if (it) {
                            "Push notifications are enabled for announcements."
                        } else {
                            "Push notifications are muted on this device."
                        }
                    }
                )
            }

            SettingsSectionHeader("App")
            SettingsCard {
                SettingsClickRow(
                    icon = Icons.Default.Language,
                    iconTint = Color(0xFF00ACC1),
                    label = "Language",
                    value = selectedLanguage,
                    onClick = {
                        selectedLanguage = if (selectedLanguage == "English") "Bahasa Melayu" else "English"
                        statusMessage = "Language preference changed to $selectedLanguage."
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                SettingsClickRow(
                    icon = Icons.Default.Tune,
                    iconTint = Color(0xFF43A047),
                    label = "Preferences",
                    value = if (compactLayoutEnabled) "Compact cards" else "Default layout",
                    onClick = {
                        compactLayoutEnabled = !compactLayoutEnabled
                        statusMessage = if (compactLayoutEnabled) {
                            "Compact layout preview enabled for dashboard cards."
                        } else {
                            "Dashboard cards restored to the default spacing."
                        }
                    }
                )
            }

            SettingsSectionHeader("Privacy & Security")
            SettingsCard {
                SettingsClickRow(
                    icon = Icons.Default.Lock,
                    iconTint = Color(0xFFFB8C00),
                    label = "Change Password",
                    value = "",
                    onClick = {
                        dialogTitle = "Change Password"
                        dialogMessage = "Password reset is not connected yet, but this action is now wired. Link it to your backend flow when ready."
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                SettingsClickRow(
                    icon = Icons.Default.Security,
                    iconTint = Color(0xFF1A3C8F),
                    label = "Two-Factor Authentication",
                    value = if (twoFactorEnabled) "Enabled" else "Not Enabled",
                    onClick = {
                        twoFactorEnabled = !twoFactorEnabled
                        statusMessage = if (twoFactorEnabled) {
                            "Two-factor authentication is marked as enabled."
                        } else {
                            "Two-factor authentication is marked as disabled."
                        }
                    }
                )
            }

            SettingsSectionHeader("About")
            SettingsCard {
                SettingsClickRow(
                    icon = Icons.Default.Info,
                    iconTint = Color(0xFF78909C),
                    label = "App Version",
                    value = "v1.0.0-prototype",
                    onClick = {
                        dialogTitle = "App Version"
                        dialogMessage = "HR App prototype\nVersion 1.0.0\nCompose Multiplatform build"
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (dialogTitle != null) {
        AlertDialog(
            onDismissRequest = { dialogTitle = null },
            title = { Text(dialogTitle!!, fontWeight = FontWeight.Bold) },
            text = { Text(dialogMessage, color = MaterialTheme.colorScheme.onSurfaceVariant) },
            confirmButton = {
                Text(
                    text = "Close",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { dialogTitle = null }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        )
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
    )
}

@Composable
private fun StatusCard(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
        )
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(content = content)
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: ImageVector,
    iconTint: Color,
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconTint.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = if (ThemeState.isDarkModeEnabled == true || isSystemInDarkTheme()) {
                    Color(0xFF64B5F6)
                } else {
                    Color(0xFF1A3C8F)
                }
            )
        )
    }
}

@Composable
private fun SettingsClickRow(
    icon: ImageVector,
    iconTint: Color,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconTint.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(22.dp))
        }
        Spacer(Modifier.width(14.dp))
        Text(
            label,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )
        if (value.isNotEmpty()) {
            Text(
                value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )
            Spacer(Modifier.width(4.dp))
        }
        Icon(
            Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
        )
    }
}

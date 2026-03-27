package com.example.hrapp.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ThemeState {
    var isDarkModeEnabled by mutableStateOf<Boolean?>(null)
}

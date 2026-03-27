# HR App Development Documentation

Welcome to the HR App project. This document provides a guide for future developers on how to create and edit pages, along with the project's coding standards.

## Project Architecture

This project is a **Compose Multiplatform** application. The core logic and UI are shared across Android and iOS targets.

- **`composeApp/src/commonMain`**: Contains the shared UI and business logic (Kotlin).
- **`composeApp/src/androidMain`**: Contains Android-specific code and resources.
- **`composeApp/src/iosMain`**: Contains iOS-specific code.

### Folder Structure (commonMain)

- `com.example.hrapp.presentation.screens`: UI screens for each feature (Home, Attendance, Leave, etc.).
- `com.example.hrapp.presentation.components`: Reusable UI components (Buttons, Cards, etc.).
- `com.example.hrapp.theme`: Theming system (Colors, Typography, Shapes).
- `com.example.hrapp.domain.model`: Data models used across the app.

---

## How to Create a New Page (Screen)

### 1. Define the Screen Composable
Create a new Kotlin file in the relevant package under `presentation.screens`. 

```kotlin
@Composable
fun MyNewScreen() {
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Welcome to the new screen!")
        }
    }
}
```

### 2. Register the Screen in Navigation
Open `com.example.hrapp.presentation.screens.main.MainScreen.kt` and:
1. Add a new object to the `Screen` sealed class.
2. Add a `composable` destination in the `NavHost` inside `MainScreen`.

```kotlin
// In MainScreen.kt
sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    // ... other screens
    object NewScreen : Screen("new_screen", "New", Icons.Default.Add)
}

// In the NavHost
composable(Screen.NewScreen.route) {
    MyNewScreen()
}
```

---

## Coding Standards & Patterns

### 1. UI Components (Compose)
- **Prefer Material 3**: Use `androidx.compose.material3` components whenever possible.
- **Reusable Components**: Check `presentation.components` before creating new UI elements. If you create a custom button or card, make it generic and place it there.
- **Theming**: Always use `MaterialTheme.colorScheme` and `MaterialTheme.typography` instead of hardcoding values. Refer to `theme/Color.kt` for project-specific colors.

### 2. Design System Alignment
The app's design is based on the HTML reference found in the `references/htmldesign` folder. 
- **Icons**: Use `androidx.compose.material.icons.Icons`.
- **Spacing**: Use a base grid of **8dp**. Standard horizontal padding for screens is **20.dp**.
- **Rounded Corners**: Standard corner radius is **12.dp** for cards and **8.dp** for buttons.

### 3. Data Flow
- Use **Mock Data Functions**: For UI prototyping, use functions like `getMockAttendanceRecords()` to provide data.
- **Sealed Classes for State**: Use sealed classes or simple data classes to represent UI states.

---

## Build and Run

### Android
Ensure you have the **JDK 17** (Android Studio's JBR is recommended) configured in your `gradle.properties`.

Run from the root:
```bash
.\gradlew.bat :composeApp:installDebug
```

### Troubleshooting
- **Unresolved @Preview**: Standard `@Preview` annotations from Android are not supported in `commonMain`. Avoid using them in shared code.
- **JDK Error**: Ensure `org.gradle.java.home` in `gradle.properties` points to a valid **JDK** (not a JRE).

# HR Mobile App - Design & Development Guideline

**Version:** 1.0  
**Platform:** Android & iOS (Kotlin Multiplatform)  
**Last Updated:** February 2026  
**Design Standards:** Material Design 3 (Android) | iOS Human Interface Guidelines

---

## Table of Contents

1. [Executive Summary](#executive-summary)
2. [Design Philosophy](#design-philosophy)
3. [Design System](#design-system)
4. [Navigation Architecture](#navigation-architecture)
5. [Screen Specifications](#screen-specifications)
6. [Component Library](#component-library)
7. [Interaction Patterns](#interaction-patterns)
8. [Mock Data Schema](#mock-data-schema)
9. [Implementation Guidelines](#implementation-guidelines)

---

## Executive Summary

### App Overview
A comprehensive enterprise HR management mobile application enabling employees to manage attendance, leave requests, view announcements, and access team information with a modern, intuitive interface.

### Core Features
1. **Authentication** - Secure login with biometric support
2. **Dashboard** - Personalized overview with quick stats
3. **Attendance Management** - QR-based check-in/out system
4. **Leave Management** - Request, track, and manage time off
5. **Announcements** - Company-wide communications
6. **Profile Management** - Personal settings and preferences

---

## Design Philosophy

### Principles

1. **Clarity First** - Information hierarchy is immediately clear
2. **Efficiency** - Common tasks require minimal steps
3. **Consistency** - Design patterns are reused throughout
4. **Delight** - Smooth, purposeful animations
5. **Accessibility** - Works for users of all abilities

---

## Design System

### Color Palette

**Primary Brand Color: Ocean Blue**
```
Light Mode:
- Primary 500: #4A90E2 (Main)
- Primary 700: #2E5C8A (Dark)
- Primary 300: #7AB4F5 (Light)

Dark Mode:
- Primary 500: #5FA3F5 (Adjusted for contrast)
```

**Secondary Color: Emerald Green**
```
- Secondary 500: #50C878 (Success)
- Warning 500: #FFA726
- Error 500: #EF5350
- Info 500: #29B6F6
```

**Surface & Background**
```
Light Mode:
- Background: #FFFFFF
- Surface: #F5F7FA
- Border: #E0E0E0

Dark Mode:
- Background: #121212
- Surface: #1E1E1E
- Border: #333333
```

### Typography

**Font Families**
- Android: Roboto
- iOS: SF Pro

**Scale**
```
Display Large: 32sp/pt, Bold
Display Medium: 28sp/pt, Bold
Title Large: 22sp/pt, Semi-Bold
Title Medium: 18sp/pt, Medium
Body Large: 16sp/pt, Regular
Body Medium: 14sp/pt, Regular
Label Large: 14sp/pt, Semi-Bold
```

### Spacing System

**8dp Grid System**
```
4dp  = xxxs
8dp  = xxs
12dp = xs
16dp = sm
24dp = md
32dp = lg
48dp = xl
64dp = xxl
```

### Elevation & Shadows

```
Level 1: 0dp 1dp 3dp rgba(0,0,0,0.12)
Level 2: 0dp 2dp 6dp rgba(0,0,0,0.16)
Level 3: 0dp 4dp 12dp rgba(0,0,0,0.20)
```

---

## Navigation Architecture

### Navigation Flow

```
Splash Screen (S0)
    ↓
Login (S1) ←→ Forgot Password (S1.1)
    ↓
Main App (Bottom Navigation)
    ├─ Home/Dashboard (S2)
    ├─ Attendance (S3)
    │   └─ QR Scanner (S3.1)
    ├─ Leave Management (S4)
    │   └─ Apply Leave (S4.1)
    ├─ Announcements (S5)
    └─ Profile (S6)
```

---

## Screen Specifications

### S0: Splash Screen

**Layout**
- Logo: 120dp x 120dp, centered
- App Name: Display Medium, centered below logo
- Loading Indicator: 32dp, Primary color
- Background: Gradient (Primary colors)

**Behavior**
- Display Duration: 2 seconds minimum
- Auto-navigate based on auth state
- Fade transition to next screen

---

### S1: Login Screen

**Layout**
```
┌─────────────────────────────┐
│                             │
│      [Logo 120dp]           │
│                             │
│    Welcome Back             │
│  Sign in to continue        │
│                             │
│  [Email Input Field]        │
│  [Password Input Field]     │
│                             │
│  ☐ Remember me  Forgot?     │
│                             │
│  [SIGN IN Button]           │
│                             │
│  Don't have account? Sign Up│
│                             │
└─────────────────────────────┘
```

**Components**
- Email Input: 56dp height, email keyboard
- Password Input: 56dp height, toggle visibility icon
- Sign In Button: 48dp height, full width, Primary color
- Remember Me: Checkbox with label
- Forgot Password: Text link, Primary color

**Validation**
- Email: Valid format required
- Password: Minimum 8 characters
- Show inline errors below fields

---

### S2: Home/Dashboard Screen

**Layout**
```
┌─────────────────────────────┐
│ 🎯 Good Morning, John   🔔  │ ← Header
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │ Today's Status Card     │ │ ← Status
│ │ ✓ Checked In at 9:00 AM │ │
│ └─────────────────────────┘ │
│                             │
│ ┌────────┐ ┌────────┐      │
│ │ 08 Days│ │ 05 Days│      │ ← Stats Grid
│ │ Present│ │ Leave  │      │
│ └────────┘ └────────┘      │
│                             │
│ Quick Actions               │
│ [📸] [📝] [👥] [📅]        │ ← Actions
│                             │
│ 📢 Announcements            │
│ ┌─────────────────────────┐ │
│ │ Company Holiday         │ │ ← Cards
│ │ Dec 25 will be...       │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

**Components**

1. **Header Bar** (64dp)
   - Greeting text with user name
   - Notification bell icon (badge if unread)

2. **Status Card** (Auto height, ~80dp)
   - Gradient background (Primary colors)
   - Check-in status with time
   - Working duration (live update)
   - Tap to navigate to Attendance

3. **Stats Grid** (2x2, 90dp each)
   - Present Days
   - Leave Balance
   - Hours Worked
   - Performance Score

4. **Quick Actions** (Horizontal scroll)
   - Scan QR
   - Apply Leave
   - Team Directory
   - Calendar View

5. **Announcements** (List, max 3 shown)
   - Icon, title, excerpt
   - Timestamp
   - Tap for detail

---

### S3: Attendance Screen

**Layout**
```
┌─────────────────────────────┐
│ [<] Attendance           ⋮  │ ← Top Bar
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │ Today's Status          │ │
│ │ ✓ Checked In 09:00 AM   │ │
│ │ Working: 6h 30m         │ │
│ │ [CHECK OUT Button]      │ │
│ └─────────────────────────┘ │
│                             │
│ ┌─────────────────────────┐ │
│ │   [QR Icon 64dp]        │ │
│ │   Scan QR Code          │ │
│ │ [SCAN QR CODE Button]   │ │
│ └─────────────────────────┘ │
│                             │
│ This Month ▼    📊 Export   │
│                             │
│ ┌─────────────────────────┐ │
│ │ 📅 Tue, Jan 14, 2026    │ │
│ │ In: 09:00  Out: 06:00   │ │
│ │ Duration: 8h 30m    ✓   │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
```

**Components**

1. **Today's Status Card**
   - Gradient background (Success green if checked in)
   - Status text with icon
   - Live duration counter
   - Check Out button (44dp height)

2. **QR Scan Card**
   - QR icon illustration
   - Instructional text
   - Scan button (48dp, outlined)

3. **Filter Row**
   - Period dropdown
   - Export button

4. **History List**
   - Date with icon
   - Check-in/out times
   - Duration
   - Status indicator

---

### S3.1: QR Scanner Screen

**Layout**
```
┌─────────────────────────────┐
│ ×                        💡 │ ← Controls
│                             │
│      ╔═══════════╗          │
│      ║  CAMERA   ║          │
│   ┏━━┛           ┗━━┓       │ ← Frame
│   ┃   SCAN AREA    ┃       │
│   ┗━━┓           ┏━━┛       │
│      ║           ║          │
│      ╚═══════════╝          │
│                             │
│ Align QR code in frame      │
│                             │
│ [ENTER CODE MANUALLY]       │
└─────────────────────────────┘
```

**Features**
- Fullscreen camera view
- Animated scanning frame (280dp square)
- Corner indicators with pulse animation
- Flash toggle button
- Manual entry option
- Success/error feedback overlays

---

### S4: Leave Management Screen

**Layout**
```
┌─────────────────────────────┐
│ [<] Leave Management     +  │
├─────────────────────────────┤
│ ┌─────────────────────────┐ │
│ │ Available Leave Balance │ │
│ │ [12] [06] [03]          │ │
│ │ Annual Sick Casual      │ │
│ └─────────────────────────┘ │
│                             │
│ ┏All┓ Pending | Approved    │ ← Tabs
│                             │
│ ┌─────────────────────────┐ │
│ │ 🌴 Annual Leave         │ │
│ │ Jan 20-22, 2026 (3d)    │ │
│ │ Status: Pending         │ │
│ └─────────────────────────┘ │
└─────────────────────────────┘
       [+ APPLY LEAVE] ← FAB
```

**Components**

1. **Balance Card**
   - Grid of leave types
   - Days available per type
   - Gradient backgrounds

2. **Filter Tabs**
   - All, Pending, Approved, Rejected
   - Active tab highlighted

3. **Leave Request Cards**
   - Leave type icon
   - Date range
   - Duration
   - Status badge

4. **FAB** (56dp)
   - Fixed bottom-right
   - Opens Apply Leave form

---

### S4.1: Apply Leave Form

**Layout**
```
┌─────────────────────────────┐
│ [<] Apply Leave             │
├─────────────────────────────┤
│                             │
│ Leave Type *                │
│ [Annual Leave      ▼]       │
│                             │
│ Start Date *                │
│ [Jan 20, 2026      📅]      │
│                             │
│ End Date *                  │
│ [Jan 22, 2026      📅]      │
│                             │
│ Duration: 3 days            │
│                             │
│ Reason *                    │
│ ┌─────────────────────────┐ │
│ │                         │ │
│ │                         │ │
│ └─────────────────────────┘ │
│ 0/500 characters            │
│                             │
│ [SUBMIT REQUEST]            │
└─────────────────────────────┘
```

**Validation**
- All fields marked * are required
- Start date cannot be in the past
- End date must be after start date
- Reason: 10-500 characters

---

## Component Library

### Buttons

**Primary Button**
```
Height: 48dp
Padding: 24dp horizontal
Background: Primary 500
Text: Label Large, White
Border Radius: 8dp
Shadow: Level 1
States: Default, Pressed, Disabled
```

**Outlined Button**
```
Height: 48dp
Border: 2dp solid Primary
Text: Primary color
Background: Transparent
```

**Text Button**
```
Height: 40dp
Text: Primary color
Background: Transparent
Ripple: Primary 10%
```

### Input Fields

**Text Input**
```
Height: 56dp
Border: 1dp solid Border Default
Border Radius: 8dp
Padding: 16dp
States: Default, Focused, Error, Disabled
Label: Floating on focus
```

### Cards

**Standard Card**
```
Border Radius: 12dp
Padding: 16dp
Shadow: Level 1
Background: Surface Primary
Border: 1dp solid Border Subtle
```

### Bottom Navigation

```
Height: 64dp
Items: 5 maximum
Icon: 24dp
Label: Label Medium
Active: Primary color, filled icon
Inactive: Text Secondary, outline icon
```

---

## Interaction Patterns

### Transitions

**Screen Transitions**
- Push: Slide left (300ms)
- Pop: Slide right (300ms)
- Modal: Slide up (300ms)
- Tab Switch: Fade (200ms)

**Animations**
- Button Press: Scale 0.95 (100ms)
- Card Tap: Ripple effect
- Loading: Circular progress
- Success: Checkmark animation
- Error: Shake animation

### Gestures

- **Swipe**: Navigate between tabs
- **Pull-to-Refresh**: Refresh content
- **Long Press**: Show context menu
- **Pinch**: Zoom (where applicable)

---

## Mock Data Schema

### User
```json
{
  "id": "USR001",
  "name": "John Doe",
  "email": "john.doe@company.com",
  "department": "Engineering",
  "position": "Senior Developer",
  "avatar": "https://example.com/avatar.jpg"
}
```

### Attendance Record
```json
{
  "id": "ATD001",
  "date": "2026-01-15",
  "checkIn": "09:00:00",
  "checkOut": "18:00:00",
  "duration": "8h 30m",
  "status": "complete"
}
```

### Leave Request
```json
{
  "id": "LVE001",
  "type": "annual",
  "startDate": "2026-01-20",
  "endDate": "2026-01-22",
  "duration": 3,
  "reason": "Family vacation",
  "status": "pending",
  "appliedDate": "2026-01-10"
}
```

### Announcement
```json
{
  "id": "ANN001",
  "title": "Company Holiday",
  "content": "December 25 will be a paid holiday...",
  "category": "company",
  "publishedDate": "2026-01-15T10:00:00Z",
  "author": "HR Department"
}
```

---

## Implementation Guidelines

### Android (Jetpack Compose)

```kotlin
// Example: Primary Button
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
```

### iOS (SwiftUI)

```swift
// Example: Primary Button
struct PrimaryButton: View {
    let title: String
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.system(size: 14, weight: .semibold))
                .foregroundColor(.white)
                .frame(maxWidth: .infinity)
                .frame(height: 48)
                .background(Color("Primary"))
                .cornerRadius(8)
        }
    }
}
```

### Shared Business Logic (Kotlin Multiplatform)

```kotlin
// Example: Authentication ViewModel
class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = authRepository.login(email, password)
                _authState.value = AuthState.Success(result)
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message)
            }
        }
    }
}
```

---

## Accessibility

### Requirements

1. **Screen Readers**
   - All interactive elements have labels
   - Meaningful content descriptions
   - Proper heading hierarchy

2. **Touch Targets**
   - Minimum 48dp x 48dp
   - Adequate spacing between elements

3. **Color Contrast**
   - Text: 4.5:1 minimum
   - Large text: 3:1 minimum
   - Interactive elements: 3:1 minimum

4. **Dynamic Type**
   - Support system font scaling
   - Test at 200% scale

---

## Performance

### Targets

- App Launch: < 2 seconds
- Screen Transition: < 300ms
- API Response: < 1 second
- Image Loading: Progressive with placeholder

### Optimization

- Lazy loading for lists
- Image caching
- Offline-first architecture
- Background sync

---

## Testing

### Test Coverage

1. **Unit Tests**: Business logic (80%+ coverage)
2. **Integration Tests**: API interactions
3. **UI Tests**: Critical user flows
4. **Accessibility Tests**: Screen reader compatibility

### Key Flows to Test

- Login/Logout
- Check-in/Check-out
- Apply Leave
- View Announcements
- Profile Update

---

**End of Document**

For questions or clarifications, contact the design team.

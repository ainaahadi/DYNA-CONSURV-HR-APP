# HR Mobile App - Design Guideline & Specification
**Version:** 2.0 (2026 Design Standards)  
**Platform:** Android & iOS  
**Technology:** Kotlin Multiplatform

---

## Table of Contents
1. [App Overview](#app-overview)
2. [Design System](#design-system)
3. [Navigation Flow & Wireframes](#navigation-flow--wireframes)
4. [Screen Specifications](#screen-specifications)
5. [Component Library](#component-library)
6. [Mock Data Structure](#mock-data-structure)
7. [Responsive Guidelines](#responsive-guidelines)

---

## App Overview

### Purpose
An enterprise HR management mobile application for employees to manage their attendance, leave requests, announcements, and team communication.

### Key Features
- Employee authentication
- Dashboard with quick stats
- Attendance tracking with QR code scanning
- Leave management system
- Company announcements
- Team directory
- Calendar view
- Profile management
- Dark/Light mode support

---

## Design System

### Color Palette

#### Light Mode
```
Primary Brand: #4A90E2 (Blue)
Primary Dark: #2E5C8A
Primary Light: #7AB4F5

Secondary: #50C878 (Green - for success states)
Warning: #FFA726 (Orange)
Error: #EF5350 (Red)

Background Primary: #FFFFFF
Background Secondary: #F5F7FA
Background Tertiary: #E8EDF2

Text Primary: #1A1A1A (90% opacity)
Text Secondary: #666666 (60% opacity)
Text Tertiary: #999999 (40% opacity)

Border: #E0E0E0
Divider: #F0F0F0

Card Background: #FFFFFF
Card Shadow: rgba(0, 0, 0, 0.08)
```

#### Dark Mode
```
Primary Brand: #5FA3F5 (Lighter Blue for contrast)
Primary Dark: #7AB4F5
Primary Light: #4A90E2

Secondary: #66D98A (Lighter Green)
Warning: #FFB84D
Error: #F77270

Background Primary: #121212
Background Secondary: #1E1E1E
Background Tertiary: #2A2A2A

Text Primary: #FFFFFF (90% opacity)
Text Secondary: #B0B0B0 (70% opacity)
Text Tertiary: #808080 (50% opacity)

Border: #333333
Divider: #2A2A2A

Card Background: #1E1E1E
Card Shadow: rgba(0, 0, 0, 0.3)
```

### Typography

#### Font Family
- **Primary:** SF Pro (iOS) / Roboto (Android)
- **Fallback:** System Default

#### Font Scales (Responsive)
```
Display Large: 32sp / 40sp line height (Headings)
Display Medium: 28sp / 36sp line height (Page Titles)
Display Small: 24sp / 32sp line height (Section Headers)

Title Large: 22sp / 28sp line height (Card Titles)
Title Medium: 18sp / 24sp line height (Subtitles)
Title Small: 16sp / 22sp line height (Labels)

Body Large: 16sp / 24sp line height (Main Content)
Body Medium: 14sp / 20sp line height (Secondary Content)
Body Small: 12sp / 16sp line height (Captions)

Label Large: 14sp / 20sp line height (Buttons)
Label Medium: 12sp / 16sp line height (Chips/Tags)
Label Small: 11sp / 14sp line height (Helper Text)
```

#### Font Weights
- Regular: 400
- Medium: 500
- Semi-Bold: 600
- Bold: 700

### Spacing System
Based on 8dp grid system:
```
xs: 4dp
sm: 8dp
md: 16dp
lg: 24dp
xl: 32dp
xxl: 48dp
```

### Border Radius
```
xs: 4dp (small chips)
sm: 8dp (buttons, inputs)
md: 12dp (cards)
lg: 16dp (large cards, modals)
xl: 24dp (bottom sheets)
full: 9999dp (circular elements)
```

### Elevation/Shadows
```
Level 1: 0dp 1dp 3dp rgba(0,0,0,0.12)
Level 2: 0dp 2dp 6dp rgba(0,0,0,0.16)
Level 3: 0dp 4dp 12dp rgba(0,0,0,0.20)
Level 4: 0dp 8dp 24dp rgba(0,0,0,0.24)
```

### Animation Standards
```
Duration Fast: 150ms
Duration Normal: 250ms
Duration Slow: 400ms

Easing Standard: cubic-bezier(0.4, 0.0, 0.2, 1)
Easing Enter: cubic-bezier(0.0, 0.0, 0.2, 1)
Easing Exit: cubic-bezier(0.4, 0.0, 1, 1)
```

---

## Navigation Flow & Wireframes

### App Navigation Structure

```
┌─────────────────────────────────────────┐
│           SPLASH SCREEN (S1)            │
│         [App Logo + Loading]            │
└─────────────────────────────────────────┘
                    │
                    ↓
          ┌─────────────────┐
          │ Is Authenticated?│
          └─────────────────┘
              │           │
         No   │           │   Yes
              ↓           ↓
┌──────────────────┐  ┌──────────────────┐
│  LOGIN (S2)      │  │  HOME (S3)       │
│  └─ Sign In Btn  │  │  └─ Stats Cards  │
│  └─ Forgot Pass  │  │  └─ Quick Actions│
│                  │  │  └─ Announcements│
└──────────────────┘  └──────────────────┘
                              │
        ┌─────────────────────┼─────────────────────┬─────────────────┐
        │                     │                     │                 │
        ↓                     ↓                     ↓                 ↓
┌──────────────┐  ┌──────────────────┐  ┌──────────────┐  ┌──────────────┐
│ ATTENDANCE   │  │ LEAVE MANAGEMENT │  │ ANNOUNCEMENTS│  │ PROFILE (S7) │
│ (S4)         │  │ (S5)             │  │ (S6)         │  │              │
│ └─ QR Scan   │  │ └─ Leave List    │  │ └─ All Posts │  │ └─ Personal  │
│ └─ Check-in  │  │ └─ Apply Leave   │  │ └─ Filter    │  │ └─ Settings  │
│ └─ History   │  │ └─ Leave Detail  │  │ └─ Detail    │  │ └─ Logout    │
└──────────────┘  └──────────────────┘  └──────────────┘  └──────────────┘
        │                     │                     
        ↓                     ↓                     
┌──────────────┐  ┌──────────────────┐  
│ QR SCANNER   │  │ APPLY LEAVE FORM │  
│ (S4.1)       │  │ (S5.1)           │  
│ └─ Camera    │  │ └─ Type Select   │  
│ └─ Flash     │  │ └─ Date Range    │  
│ └─ Success   │  │ └─ Reason        │  
└──────────────┘  └──────────────────┘  
```

### Bottom Navigation Structure

```
┌─────────────────────────────────────────────────────────┐
│                     Content Area                        │
│                  (Screen Changes Here)                  │
└─────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────┐
│  [Home]    [Attendance]   [Leave]   [Announce]  [Menu]  │
│   icon        icon         icon       icon       icon    │
│   Home     Attendance     Leave     Announce     More    │
└─────────────────────────────────────────────────────────┘
```

---

## Screen Specifications

### S1: Splash Screen

**Layout:**
```
┌─────────────────────────────────┐
│                                 │
│                                 │
│         [APP LOGO]              │
│                                 │
│      "HR Management"            │
│                                 │
│     [Loading Indicator]         │
│                                 │
│                                 │
└─────────────────────────────────┘
```

**Elements:**
- App logo (centered, 120dp x 120dp)
- App name (Display Medium, Primary Brand Color)
- Loading spinner (below app name, 32dp)
- Gradient background (Primary Light to Primary Dark)

**Behavior:**
- Display for 2 seconds
- Fade out animation (400ms)
- Navigate to Login or Home based on auth state

---

### S2: Login Screen

**Layout:**
```
┌─────────────────────────────────┐
│   [Back Button]                 │
│                                 │
│                                 │
│    [Illustration/Logo]          │
│                                 │
│    Welcome Back                 │
│    Sign in to continue          │
│                                 │
│    ┌─────────────────────────┐ │
│    │ 📧 Email               │ │
│    └─────────────────────────┘ │
│                                 │
│    ┌─────────────────────────┐ │
│    │ 🔒 Password       👁    │ │
│    └─────────────────────────┘ │
│                                 │
│    [x] Remember me              │
│                   Forgot Pass?  │
│                                 │
│    ┌─────────────────────────┐ │
│    │      SIGN IN            │ │
│    └─────────────────────────┘ │
│                                 │
│    Don't have account? Sign Up  │
│                                 │
└─────────────────────────────────┘
```

**Elements:**
- Title: "Welcome Back" (Display Medium, Text Primary)
- Subtitle: "Sign in to continue" (Body Large, Text Secondary)
- Email input field with icon
- Password input field with show/hide toggle
- Remember me checkbox
- Forgot password link (Label Medium, Primary Brand)
- Sign In button (Primary, full width, 48dp height)
- Sign Up link (Label Medium, Text Secondary)

**Interaction Flow:**
```
Login Button → 
  ├─ If Valid: Show Loading → Navigate to Home (S3)
  └─ If Invalid: Show Error Message (shake animation)

Forgot Password → Navigate to Password Reset Flow
Sign Up → Navigate to Registration Flow
```

**Mock Data:**
```json
{
  "testUser": {
    "email": "john.doe@company.com",
    "password": "••••••••",
    "rememberMe": true
  }
}
```

---

### S3: Home/Dashboard Screen

**Layout:**
```
┌─────────────────────────────────────┐
│  🎯 Logo      Good Morning, John 🔔 │ ← Header
│                                     │
│  ┌─────────────────────────────┐   │
│  │  📅 Today: Jan 15, 2026     │   │ ← Date Card
│  │  Status: ✓ Checked In       │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌──────────┐  ┌──────────┐       │
│  │  📊 08   │  │  🌴 05   │       │ ← Stats Grid
│  │  Present │  │  Leave   │       │   (2 columns)
│  └──────────┘  └──────────┘       │
│  ┌──────────┐  ┌──────────┐       │
│  │  ⏱️ 160  │  │  🎯 95%  │       │
│  │  Hours   │  │  Perform │       │
│  └──────────┘  └──────────┘       │
│                                     │
│  Quick Actions                      │ ← Section Title
│  ┌────┐ ┌────┐ ┌────┐ ┌────┐     │
│  │ 📸 │ │ 📝 │ │ 👥 │ │ 📅 │     │ ← Action Buttons
│  │Scan│ │Req │ │Team│ │Cal │     │   (Horizontal Scroll)
│  └────┘ └────┘ └────┘ └────┘     │
│                                     │
│  📢 Announcements                   │ ← Section Title
│  ┌─────────────────────────────┐   │
│  │ 🎉 Company Holiday           │   │
│  │ Dec 25 will be a paid...    │   │ ← Announcement Cards
│  │ 2 hours ago      [➜]        │   │   (Vertical List)
│  └─────────────────────────────┘   │
│  ┌─────────────────────────────┐   │
│  │ 📋 New Policy Update         │   │
│  │ Please review the updated... │   │
│  │ 5 hours ago      [➜]        │   │
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
┌─[Home]─[Attend]─[Leave]─[News]─[Menu]┐ ← Bottom Nav
```

**Header Section:**
- App logo (32dp x 32dp, top-left)
- Greeting text: "Good Morning, [Name]" (Title Medium)
- Profile avatar (top-right, 40dp circular)
- Notification bell icon (badge if new notifications)

**Status Card:**
- Current date (Title Medium, with calendar icon)
- Check-in status with checkmark icon
- Background: Card Background with Level 1 shadow
- Border radius: md (12dp)
- Padding: md (16dp)

**Stats Grid (2x2):**
- 4 stat cards in grid layout
- Each card contains:
  - Icon (24dp, Primary Brand Color)
  - Number (Display Small, Text Primary)
  - Label (Body Medium, Text Secondary)
- Card spacing: sm (8dp) between cards
- Card background: Card Background
- Border radius: md (12dp)

**Quick Actions Section:**
- Title: "Quick Actions" (Title Large, Text Primary)
- Horizontal scrollable row of action buttons
- Each button:
  - Circular icon container (64dp)
  - Icon (28dp)
  - Label below (Label Medium)
  - Background: Background Secondary
  - Ripple effect on tap

**Announcements Section:**
- Title: "Announcements" (Title Large, Text Primary)
- "View All" link (Label Medium, Primary Brand, right-aligned)
- Vertical list of announcement cards
- Each card:
  - Icon (24dp, left)
  - Title (Title Medium, Text Primary)
  - Excerpt (Body Medium, Text Secondary, 2 lines max)
  - Timestamp (Label Small, Text Tertiary)
  - Arrow icon (right)
  - Divider between cards

**Interaction Flow:**
```
Notification Bell → Navigate to Notifications Screen
Profile Avatar → Navigate to Profile Screen (S7)
Stats Card → Navigate to respective detail screen
  - Present → Attendance History
  - Leave → Leave Management (S5)
  - Hours → Time Tracking Detail
  - Performance → Performance Dashboard

Quick Actions:
  - Scan → QR Scanner (S4.1)
  - Request → Leave Application Form (S5.1)
  - Team → Team Directory
  - Calendar → Calendar View

Announcement Card → Announcement Detail (S6.1)
View All → All Announcements (S6)
```

**Mock Data:**
```json
{
  "user": {
    "name": "John Doe",
    "avatar": "avatar_url",
    "greeting": "Good Morning"
  },
  "todayStatus": {
    "date": "January 15, 2026",
    "checkInStatus": "Checked In",
    "checkInTime": "09:00 AM"
  },
  "stats": {
    "presentDays": 8,
    "leaveDays": 5,
    "hoursWorked": 160,
    "performanceScore": 95
  },
  "announcements": [
    {
      "id": 1,
      "icon": "🎉",
      "title": "Company Holiday Announcement",
      "excerpt": "December 25 will be a paid holiday for all employees...",
      "timestamp": "2 hours ago",
      "date": "2026-01-15T10:00:00Z"
    },
    {
      "id": 2,
      "icon": "📋",
      "title": "New Policy Update",
      "excerpt": "Please review the updated work from home policy...",
      "timestamp": "5 hours ago",
      "date": "2026-01-15T07:00:00Z"
    },
    {
      "id": 3,
      "icon": "🎯",
      "title": "Q1 Goals Released",
      "excerpt": "All department goals for Q1 2026 are now available...",
      "timestamp": "1 day ago",
      "date": "2026-01-14T14:00:00Z"
    }
  ]
}
```

---

### S4: Attendance Screen

**Layout:**
```
┌─────────────────────────────────────┐
│  ← Attendance                    ⋮  │ ← Header
│                                     │
│  ┌─────────────────────────────┐   │
│  │   Today's Status             │   │
│  │   ✓ Checked In at 09:00 AM  │   │ ← Status Card
│  │   Duration: 6h 30m           │   │
│  │   ┌──────────┐               │   │
│  │   │ CHECK OUT│               │   │
│  │   └──────────┘               │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │       [QR ICON]              │   │
│  │                              │   │ ← QR Scan Section
│  │    Scan QR Code              │   │
│  │    to mark attendance        │   │
│  │                              │   │
│  │   ┌──────────────────┐       │   │
│  │   │  SCAN QR CODE    │       │   │
│  │   └──────────────────┘       │   │
│  └─────────────────────────────┘   │
│                                     │
│  This Month                         │
│  ┌─────────────────────────────┐   │
│  │ 📅 Jan 14, 2026              │   │
│  │ In: 09:00 AM  Out: 06:00 PM  │   │ ← History List
│  │ Duration: 8h 30m             │   │
│  └─────────────────────────────┘   │
│  ┌─────────────────────────────┐   │
│  │ 📅 Jan 13, 2026              │   │
│  │ In: 08:45 AM  Out: 05:45 PM  │   │
│  │ Duration: 8h 30m             │   │
│  └─────────────────────────────┘   │
│  ┌─────────────────────────────┐   │
│  │ 📅 Jan 12, 2026              │   │
│  │ In: 09:15 AM  Out: 06:00 PM  │   │
│  │ Duration: 8h 15m             │   │
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
┌─[Home]─[Attend]─[Leave]─[News]─[Menu]┐
```

**Header:**
- Back button (left)
- Title: "Attendance" (Title Large)
- Menu icon (right, for filters/options)

**Today's Status Card:**
- Large checkmark icon or clock icon
- Status text: "Checked In" / "Not Checked In"
- Check-in time
- Duration counter (live update)
- CHECK OUT button (full width, Primary color)
- Background: Gradient (Primary Light to Primary)
- Text color: White

**QR Scan Section:**
- Large QR code icon illustration
- Title: "Scan QR Code" (Title Medium)
- Subtitle: "to mark attendance" (Body Medium)
- SCAN QR CODE button (outlined style)
- Background: Card Background

**History Section:**
- Title: "This Month" (Title Medium)
- Filter dropdown (right-aligned)
- List of attendance records
- Each record card:
  - Date (Title Small)
  - Check-in time
  - Check-out time
  - Total duration
  - Status indicator (color-coded)
  - Swipe for more options

**Interaction Flow:**
```
CHECK OUT Button →
  ├─ Show confirmation dialog
  └─ On confirm: Update status, show success message

SCAN QR CODE Button → Navigate to QR Scanner (S4.1)

Menu Icon (⋮) →
  ├─ View Report
  ├─ Request Correction
  └─ Settings

History Card → Navigate to Attendance Detail
  - Full day timeline
  - Location map
  - Correction option

Filter Dropdown →
  - This Week
  - This Month
  - Last Month
  - Custom Range
```

**Mock Data:**
```json
{
  "todayAttendance": {
    "status": "checked_in",
    "checkInTime": "09:00 AM",
    "checkOutTime": null,
    "duration": "6h 30m",
    "location": "Office - Floor 3"
  },
  "attendanceHistory": [
    {
      "id": 1,
      "date": "2026-01-14",
      "dateFormatted": "Jan 14, 2026",
      "checkIn": "09:00 AM",
      "checkOut": "06:00 PM",
      "duration": "8h 30m",
      "status": "complete",
      "location": "Office - Floor 3"
    },
    {
      "id": 2,
      "date": "2026-01-13",
      "dateFormatted": "Jan 13, 2026",
      "checkIn": "08:45 AM",
      "checkOut": "05:45 PM",
      "duration": "8h 30m",
      "status": "complete",
      "location": "Office - Floor 3"
    },
    {
      "id": 3,
      "date": "2026-01-12",
      "dateFormatted": "Jan 12, 2026",
      "checkIn": "09:15 AM",
      "checkOut": "06:00 PM",
      "duration": "8h 15m",
      "status": "complete",
      "location": "Office - Floor 3"
    },
    {
      "id": 4,
      "date": "2026-01-11",
      "dateFormatted": "Jan 11, 2026",
      "checkIn": "09:00 AM",
      "checkOut": "01:00 PM",
      "duration": "4h 00m",
      "status": "half_day",
      "location": "Office - Floor 3"
    },
    {
      "id": 5,
      "date": "2026-01-10",
      "dateFormatted": "Jan 10, 2026",
      "checkIn": null,
      "checkOut": null,
      "duration": "0h 00m",
      "status": "absent",
      "location": null
    }
  ],
  "monthlySummary": {
    "totalDays": 20,
    "presentDays": 18,
    "absentDays": 1,
    "halfDays": 1,
    "totalHours": 152
  }
}
```

---

### S4.1: QR Scanner Screen

**Layout:**
```
┌─────────────────────────────────────┐
│  ×                               💡  │ ← Close & Flash
│                                     │
│  ┌─────────────────────────────┐   │
│  │                             │   │
│  │        [CAMERA VIEW]        │   │
│  │                             │   │
│  │      ┌───────────────┐      │   │ ← Scanning Frame
│  │      │               │      │   │
│  │      │   QR FRAME    │      │   │
│  │      │               │      │   │
│  │      └───────────────┘      │   │
│  │                             │   │
│  │                             │   │
│  └─────────────────────────────┘   │
│                                     │
│   Align QR code within frame       │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  ENTER CODE MANUALLY        │   │
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

**Elements:**
- Full-screen camera view
- Close button (X, top-left)
- Flash toggle (top-right)
- Scanning frame (center, animated corners)
- Instruction text: "Align QR code within frame"
- Manual entry button (bottom)

**Interaction Flow:**
```
QR Code Detected →
  ├─ Vibrate feedback
  ├─ Show success animation
  └─ Navigate back to Attendance (S4) with success message

Close Button → Navigate back to Attendance (S4)

Flash Toggle → Enable/Disable camera flash

Manual Entry → Show input dialog
  └─ On submit: Validate code → Success/Error
```

---

### S5: Leave Management Screen

**Layout:**
```
┌─────────────────────────────────────┐
│  ← Leave Management              +  │ ← Header
│                                     │
│  ┌─────────────────────────────┐   │
│  │  Available Leave Balance     │   │
│  │  ┌────┐ ┌────┐ ┌────┐       │   │ ← Balance Cards
│  │  │ 12 │ │ 06 │ │ 03 │       │   │
│  │  │Ann.│ │Sick│ │Cas.│       │   │
│  │  └────┘ └────┘ └────┘       │   │
│  └─────────────────────────────┘   │
│                                     │
│  [All] [Pending] [Approved] [Rejected] ← Filter Tabs
│                                     │
│  ┌─────────────────────────────┐   │
│  │ 🌴 Annual Leave              │   │
│  │ Jan 20 - Jan 22, 2026       │   │
│  │ Status: Pending              │   │ ← Leave Cards
│  │                   [PENDING] │   │
│  └─────────────────────────────┘   │
│  ┌─────────────────────────────┐   │
│  │ 🤒 Sick Leave                │   │
│  │ Jan 10, 2026                 │   │
│  │ Status: Approved             │   │
│  │                  [APPROVED] │   │
│  └─────────────────────────────┘   │
│  ┌─────────────────────────────┐   │
│  │ 🏠 Casual Leave              │   │
│  │ Dec 28 - Dec 29, 2025       │   │
│  │ Status: Approved             │   │
│  │                  [APPROVED] │   │
│  └─────────────────────────────┘   │
│  ┌─────────────────────────────┐   │
│  │ 🌴 Annual Leave              │   │
│  │ Dec 20 - Dec 24, 2025       │   │
│  │ Status: Rejected             │   │
│  │                  [REJECTED] │   │
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
┌─────────────────────────────────────┐
│          [+] APPLY LEAVE            │ ← Floating Button
└─────────────────────────────────────┘
┌─[Home]─[Attend]─[Leave]─[News]─[Menu]┐
```

**Header:**
- Back button (left)
- Title: "Leave Management" (Title Large)
- Add button (+) (right) → Apply for leave

**Balance Card:**
- Section title: "Available Leave Balance"
- Grid of leave type cards (3 columns)
- Each card shows:
  - Number (Display Small)
  - Leave type abbreviation (Label Medium)
- Background: Gradient based on leave type
- Text color: White

**Filter Tabs:**
- Horizontal scrollable tabs
- Options: All, Pending, Approved, Rejected
- Selected tab: Primary Brand color with bottom border
- Unselected: Text Secondary color

**Leave List:**
- Scrollable list of leave request cards
- Each card contains:
  - Leave type icon and name (Title Small)
  - Date range (Body Medium)
  - Status label (Label Small)
  - Status badge (color-coded chip on right)
  - Divider between cards

**Status Badge Colors:**
- Pending: Warning color (#FFA726)
- Approved: Secondary/Success color (#50C878)
- Rejected: Error color (#EF5350)

**Floating Action Button (FAB):**
- Fixed at bottom
- Text: "+ APPLY LEAVE"
- Background: Primary Brand
- Shadow: Level 3
- Full width with margin

**Interaction Flow:**
```
+ Button (Header/FAB) → Navigate to Apply Leave Form (S5.1)

Balance Card Tap → Show leave policy details modal

Filter Tab → Filter leave list by status

Leave Card Tap → Navigate to Leave Detail (S5.2)
  - View full details
  - Reason
  - Approver information
  - Cancel option (if pending)

Leave Card Swipe →
  - If Pending: Show Cancel option
  - If Approved: Show View option
  - If Rejected: Show Reapply option
```

**Mock Data:**
```json
{
  "leaveBalance": {
    "annual": {
      "total": 15,
      "used": 3,
      "available": 12
    },
    "sick": {
      "total": 10,
      "used": 4,
      "available": 6
    },
    "casual": {
      "total": 5,
      "used": 2,
      "available": 3
    }
  },
  "leaveRequests": [
    {
      "id": 1,
      "type": "annual",
      "typeName": "Annual Leave",
      "icon": "🌴",
      "startDate": "2026-01-20",
      "endDate": "2026-01-22",
      "dateDisplay": "Jan 20 - Jan 22, 2026",
      "days": 3,
      "reason": "Family vacation to Bali",
      "status": "pending",
      "appliedDate": "2026-01-15",
      "approver": "Sarah Johnson"
    },
    {
      "id": 2,
      "type": "sick",
      "typeName": "Sick Leave",
      "icon": "🤒",
      "startDate": "2026-01-10",
      "endDate": "2026-01-10",
      "dateDisplay": "Jan 10, 2026",
      "days": 1,
      "reason": "Flu and fever",
      "status": "approved",
      "appliedDate": "2026-01-10",
      "approver": "Sarah Johnson",
      "approvedDate": "2026-01-10"
    },
    {
      "id": 3,
      "type": "casual",
      "typeName": "Casual Leave",
      "icon": "🏠",
      "startDate": "2025-12-28",
      "endDate": "2025-12-29",
      "dateDisplay": "Dec 28 - Dec 29, 2025",
      "days": 2,
      "reason": "Personal work",
      "status": "approved",
      "appliedDate": "2025-12-20",
      "approver": "Sarah Johnson",
      "approvedDate": "2025-12-22"
    },
    {
      "id": 4,
      "type": "annual",
      "typeName": "Annual Leave",
      "icon": "🌴",
      "startDate": "2025-12-20",
      "endDate": "2025-12-24",
      "dateDisplay": "Dec 20 - Dec 24, 2025",
      "days": 5,
      "reason": "Year-end vacation",
      "status": "rejected",
      "appliedDate": "2025-12-10",
      "approver": "Sarah Johnson",
      "rejectedDate": "2025-12-12",
      "rejectionReason": "Insufficient staffing during holiday season"
    }
  ]
}
```

---

### S5.1: Apply Leave Form Screen

**Layout:**
```
┌─────────────────────────────────────┐
│  ← Apply for Leave                  │ ← Header
│                                     │
│  Leave Type *                       │
│  ┌─────────────────────────────┐   │
│  │ Select leave type       ▼   │   │ ← Dropdown
│  └─────────────────────────────┘   │
│                                     │
│  Start Date *                       │
│  ┌─────────────────────────────┐   │
│  │ Select start date     📅    │   │ ← Date Picker
│  └─────────────────────────────┘   │
│                                     │
│  End Date *                         │
│  ┌─────────────────────────────┐   │
│  │ Select end date       📅    │   │ ← Date Picker
│  └─────────────────────────────┘   │
│                                     │
│  ℹ️ Total Days: 3 days              │ ← Calculated
│                                     │
│  Reason *                           │
│  ┌─────────────────────────────┐   │
│  │ Briefly describe your       │   │
│  │ reason for leave...         │   │ ← Text Area
│  │                             │   │
│  │                             │   │
│  └─────────────────────────────┘   │
│                                     │
│  Attachment (Optional)              │
│  ┌─────────────────────────────┐   │
│  │  📎 Attach Document         │   │ ← File Upload
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │       SUBMIT REQUEST        │   │ ← Submit Button
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

**Header:**
- Back button (left)
- Title: "Apply for Leave" (Title Large)

**Form Fields:**

1. **Leave Type Dropdown:**
   - Label: "Leave Type *" (Title Small)
   - Options: Annual Leave, Sick Leave, Casual Leave, Maternity/Paternity, Unpaid Leave
   - Show available balance below selection
   - Required field indicator (*)

2. **Start Date Picker:**
   - Label: "Start Date *" (Title Small)
   - Calendar icon (right)
   - Opens date picker modal
   - Required field indicator (*)

3. **End Date Picker:**
   - Label: "End Date *" (Title Small)
   - Calendar icon (right)
   - Opens date picker modal
   - Must be >= start date
   - Required field indicator (*)

4. **Days Calculator:**
   - Auto-calculated display
   - Shows total days between dates
   - Info icon with calculation logic
   - Example: "ℹ️ Total Days: 3 days"

5. **Reason Text Area:**
   - Label: "Reason *" (Title Small)
   - Multi-line input (min 20 chars)
   - Character counter (max 500)
   - Required field indicator (*)

6. **Attachment Upload:**
   - Label: "Attachment (Optional)" (Title Small)
   - File upload button
   - Supported formats: PDF, JPG, PNG
   - Max size: 5MB
   - Show preview after upload

**Submit Button:**
- Text: "SUBMIT REQUEST"
- Full width
- Primary color
- Disabled until required fields are filled
- Show loading state on submit

**Interaction Flow:**
```
Leave Type Dropdown → Show dropdown modal
  └─ On select: Display available balance

Start/End Date → Show calendar picker
  └─ On select: Auto-calculate total days

Reason Text Area → Validate min/max length

Attachment Button → Open file picker
  └─ On select: Upload & show preview

SUBMIT Button →
  ├─ Validate all fields
  ├─ If valid: Show loading
  ├─ Submit request
  ├─ Show success message
  └─ Navigate back to Leave Management (S5)
  
  └─ If invalid: Show error messages for each field
```

**Validation Rules:**
- Leave Type: Required
- Start Date: Required, cannot be past date
- End Date: Required, must be >= start date
- Reason: Required, min 20 characters, max 500
- Check available balance before submission

**Mock Data:**
```json
{
  "leaveTypes": [
    {
      "id": "annual",
      "name": "Annual Leave",
      "icon": "🌴",
      "available": 12,
      "total": 15
    },
    {
      "id": "sick",
      "name": "Sick Leave",
      "icon": "🤒",
      "available": 6,
      "total": 10
    },
    {
      "id": "casual",
      "name": "Casual Leave",
      "icon": "🏠",
      "available": 3,
      "total": 5
    },
    {
      "id": "maternity",
      "name": "Maternity/Paternity",
      "icon": "👶",
      "available": 90,
      "total": 90
    },
    {
      "id": "unpaid",
      "name": "Unpaid Leave",
      "icon": "📝",
      "available": null,
      "total": null
    }
  ],
  "formSubmission": {
    "leaveType": "annual",
    "startDate": "2026-01-20",
    "endDate": "2026-01-22",
    "totalDays": 3,
    "reason": "Family vacation to Bali for my daughter's birthday celebration.",
    "attachment": null,
    "submittedAt": "2026-01-15T14:30:00Z"
  }
}
```

---

### S6: Announcements Screen

**Layout:**
```
┌─────────────────────────────────────┐
│  ← Announcements                 🔍  │ ← Header
│                                     │
│  [All] [Company] [HR] [Events]      │ ← Filter Tabs
│                                     │
│  ┌─────────────────────────────┐   │
│  │ 🎉                          │   │
│  │ Company Holiday             │   │
│  │ Announcement                │   │
│  │                             │   │
│  │ December 25 will be a paid  │   │
│  │ holiday for all employees.  │   │ ← Featured Card
│  │ Enjoy time with family!     │   │
│  │                             │   │
│  │ 📅 Posted 2 hours ago       │   │
│  │ 👁️ 234 views                │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │ 📋 New Policy Update         │   │
│  │ Please review the updated   │   │
│  │ work from home policy...    │   │ ← Regular Cards
│  │                             │   │
│  │ 5 hours ago • HR            │   │
│  └─────────────────────────────┘   │
│  ┌─────────────────────────────┐   │
│  │ 🎯 Q1 Goals Released         │   │
│  │ All department goals for    │   │
│  │ Q1 2026 are now available...│   │
│  │                             │   │
│  │ 1 day ago • Company         │   │
│  └─────────────────────────────┘   │
│  ┌─────────────────────────────┐   │
│  │ 🎊 Team Building Event       │   │
│  │ Join us for team building   │   │
│  │ activities next Friday...   │   │
│  │                             │   │
│  │ 2 days ago • Events         │   │
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
┌─[Home]─[Attend]─[Leave]─[News]─[Menu]┐
```

**Header:**
- Back button (left)
- Title: "Announcements" (Title Large)
- Search icon (right)

**Filter Tabs:**
- Horizontal scrollable tabs
- Options: All, Company, HR, Events, IT, Finance
- Selected tab: Primary Brand color with bottom border
- Unselected: Text Secondary color

**Featured Announcement Card (First Item):**
- Larger card with prominent styling
- Large icon/image at top
- Title (Title Large, Text Primary)
- Full content preview
- Metadata row:
  - Post date (Label Small)
  - View count (Label Small)
- Background: Card Background with gradient overlay
- Shadow: Level 2

**Regular Announcement Cards:**
- Compact card layout
- Icon (left, 40dp)
- Title (Title Medium)
- Excerpt (Body Medium, 2 lines)
- Metadata: timestamp • category
- Divider between cards
- Ripple effect on tap

**Interaction Flow:**
```
Search Icon → Open search overlay
  └─ Search by title/content

Filter Tab → Filter announcements by category

Announcement Card → Navigate to Detail (S6.1)
  - Full content
  - Attachments
  - Share option
  - Comments (if enabled)

Pull to Refresh → Reload announcements

Infinite Scroll → Load more announcements
```

**Mock Data:**
```json
{
  "announcements": [
    {
      "id": 1,
      "icon": "🎉",
      "title": "Company Holiday Announcement",
      "excerpt": "December 25 will be a paid holiday for all employees...",
      "content": "December 25 will be a paid holiday for all employees. Enjoy quality time with your family and loved ones. The office will remain closed and will reopen on December 26.",
      "category": "Company",
      "timestamp": "2 hours ago",
      "postedAt": "2026-01-15T10:00:00Z",
      "views": 234,
      "isFeatured": true,
      "author": {
        "name": "HR Department",
        "avatar": "avatar_url"
      }
    },
    {
      "id": 2,
      "icon": "📋",
      "title": "New Policy Update",
      "excerpt": "Please review the updated work from home policy...",
      "content": "Please review the updated work from home policy. All employees are now eligible for 2 days of remote work per week. Please coordinate with your manager.",
      "category": "HR",
      "timestamp": "5 hours ago",
      "postedAt": "2026-01-15T07:00:00Z",
      "views": 156,
      "isFeatured": false,
      "author": {
        "name": "Sarah Johnson",
        "avatar": "avatar_url"
      },
      "attachments": [
        {
          "name": "WFH_Policy_2026.pdf",
          "url": "file_url",
          "size": "245 KB"
        }
      ]
    },
    {
      "id": 3,
      "icon": "🎯",
      "title": "Q1 Goals Released",
      "excerpt": "All department goals for Q1 2026 are now available...",
      "content": "All department goals for Q1 2026 are now available in the portal. Please review your team's objectives and align your personal goals accordingly.",
      "category": "Company",
      "timestamp": "1 day ago",
      "postedAt": "2026-01-14T14:00:00Z",
      "views": 312,
      "isFeatured": false,
      "author": {
        "name": "Michael Chen",
        "avatar": "avatar_url"
      }
    },
    {
      "id": 4,
      "icon": "🎊",
      "title": "Team Building Event",
      "excerpt": "Join us for team building activities next Friday...",
      "content": "Join us for team building activities next Friday at Central Park. Activities include sports, games, and a BBQ lunch. RSVP by Wednesday.",
      "category": "Events",
      "timestamp": "2 days ago",
      "postedAt": "2026-01-13T09:00:00Z",
      "views": 189,
      "isFeatured": false,
      "author": {
        "name": "Events Committee",
        "avatar": "avatar_url"
      }
    }
  ],
  "categories": [
    { "id": "all", "name": "All" },
    { "id": "company", "name": "Company" },
    { "id": "hr", "name": "HR" },
    { "id": "events", "name": "Events" },
    { "id": "it", "name": "IT" },
    { "id": "finance", "name": "Finance" }
  ]
}
```

---

### S7: Profile/Menu Screen

**Layout:**
```
┌─────────────────────────────────────┐
│  ← Profile                       ⚙️  │ ← Header
│                                     │
│         ┌─────────────┐             │
│         │  [AVATAR]   │             │
│         └─────────────┘             │ ← Profile Header
│         John Doe                    │
│         john.doe@company.com        │
│         Employee ID: EMP-12345      │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  Personal Information        │   │
│  │  ┌─────────────────────────┐│   │
│  │  │ 👤 Edit Profile      > ││   │
│  │  ├─────────────────────────┤│   │
│  │  │ 📱 Contact Details   > ││   │ ← Menu Sections
│  │  ├─────────────────────────┤│   │
│  │  │ 🏢 Department Info   > ││   │
│  │  └─────────────────────────┘│   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  Preferences                 │   │
│  │  ┌─────────────────────────┐│   │
│  │  │ 🌙 Dark Mode       [◉○]││   │
│  │  ├─────────────────────────┤│   │
│  │  │ 🔔 Notifications   [○◉]││   │
│  │  ├─────────────────────────┤│   │
│  │  │ 🌐 Language        EN > ││   │
│  │  └─────────────────────────┘│   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  Support                     │   │
│  │  ┌─────────────────────────┐│   │
│  │  │ ❓ Help Center       > ││   │
│  │  ├─────────────────────────┤│   │
│  │  │ 📝 Privacy Policy    > ││   │
│  │  ├─────────────────────────┤│   │
│  │  │ ℹ️ About             > ││   │
│  │  └─────────────────────────┘│   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │       🚪 LOGOUT              │   │
│  └─────────────────────────────┘   │
│                                     │
│         App Version 2.0.1           │
│                                     │
└─────────────────────────────────────┘
┌─[Home]─[Attend]─[Leave]─[News]─[Menu]┐
```

**Header:**
- Back button (left)
- Title: "Profile" (Title Large)
- Settings icon (right)

**Profile Header Section:**
- Large circular avatar (96dp)
- Edit photo button (overlay on avatar)
- Name (Title Large)
- Email (Body Medium, Text Secondary)
- Employee ID (Label Small, Text Tertiary)
- Background: Gradient (subtle)

**Menu Sections:**

1. **Personal Information:**
   - Section title (Title Small)
   - Edit Profile → Navigate to edit form
   - Contact Details → Phone, emergency contact
   - Department Info → Team, manager, location

2. **Preferences:**
   - Section title (Title Small)
   - Dark Mode toggle switch
   - Notifications toggle switch
   - Language selector → List of languages

3. **Support:**
   - Section title (Title Small)
   - Help Center → FAQ, contact support
   - Privacy Policy → Policy document
   - About → App info, terms of service

**Logout Button:**
- Full width button
- Outlined style with Error color
- Logout icon
- Confirmation dialog before logout

**App Version:**
- Small text at bottom
- Text Tertiary color
- Center aligned

**Interaction Flow:**
```
Avatar/Edit Photo → Open image picker
  └─ Crop & upload new photo

Edit Profile → Navigate to edit form
  - Name
  - Phone
  - Address
  - Emergency contact

Contact Details → Show contact info screen

Department Info → Show department details
  - Department name
  - Manager info
  - Office location
  - Team members

Dark Mode Toggle → Switch theme immediately
  └─ Animate theme transition

Notifications Toggle → Update notification preferences

Language Selector → Show language list
  └─ Change app language

Help Center → Navigate to help/FAQ

Privacy Policy → Show policy document

About → Show app information
  - Version
  - Terms of service
  - Licenses
  - Credits

LOGOUT Button → Show confirmation dialog
  └─ On confirm:
      - Clear session
      - Clear cached data
      - Navigate to Login (S2)
```

**Mock Data:**
```json
{
  "profile": {
    "id": "EMP-12345",
    "name": "John Doe",
    "email": "john.doe@company.com",
    "phone": "+1 (555) 123-4567",
    "avatar": "avatar_url",
    "department": {
      "name": "Engineering",
      "team": "Frontend Development",
      "manager": "Sarah Johnson",
      "location": "Office - Floor 3"
    },
    "emergencyContact": {
      "name": "Jane Doe",
      "relationship": "Spouse",
      "phone": "+1 (555) 987-6543"
    },
    "address": {
      "street": "123 Main Street",
      "city": "San Francisco",
      "state": "CA",
      "zip": "94102",
      "country": "USA"
    }
  },
  "preferences": {
    "darkMode": false,
    "notifications": true,
    "language": "en",
    "languageName": "English"
  },
  "appInfo": {
    "version": "2.0.1",
    "buildNumber": "2024",
    "releaseDate": "2026-01-01"
  },
  "languages": [
    { "code": "en", "name": "English" },
    { "code": "es", "name": "Español" },
    { "code": "fr", "name": "Français" },
    { "code": "de", "name": "Deutsch" },
    { "code": "zh", "name": "中文" },
    { "code": "ja", "name": "日本語" }
  ]
}
```

---

## Component Library

### Buttons

#### Primary Button
```
Style:
- Background: Primary Brand Color
- Text: White
- Height: 48dp
- Border Radius: sm (8dp)
- Padding: md (16dp) horizontal
- Font: Label Large, Semi-Bold
- Ripple Effect: White 20% opacity
- Disabled: 40% opacity

States:
- Default: Full color
- Pressed: 90% color + ripple
- Disabled: 40% opacity
- Loading: Show spinner, disable interaction
```

#### Secondary Button (Outlined)
```
Style:
- Background: Transparent
- Border: 1.5dp solid Primary Brand
- Text: Primary Brand Color
- Height: 48dp
- Border Radius: sm (8dp)
- Padding: md (16dp) horizontal
- Font: Label Large, Medium
- Ripple Effect: Primary 10% opacity
```

#### Text Button
```
Style:
- Background: Transparent
- Text: Primary Brand Color
- Height: 40dp
- Padding: sm (8dp) horizontal
- Font: Label Large, Medium
- Ripple Effect: Primary 10% opacity
```

#### Icon Button
```
Style:
- Background: Transparent
- Size: 40dp x 40dp
- Icon Size: 24dp
- Border Radius: full
- Ripple Effect: Text 10% opacity
```

### Input Fields

#### Text Input
```
Style:
- Height: 56dp
- Border: 1dp solid Border color
- Border Radius: sm (8dp)
- Padding: md (16dp)
- Font: Body Large
- Background: Card Background

States:
- Default: Border color
- Focused: Primary Brand border (2dp)
- Error: Error color border
- Disabled: 50% opacity

Label:
- Position: Floating (inside field, moves up on focus)
- Font: Label Medium
- Color: Text Secondary
```

#### Text Area
```
Style:
- Min Height: 120dp
- Border: 1dp solid Border color
- Border Radius: sm (8dp)
- Padding: md (16dp)
- Font: Body Large
- Background: Card Background
- Character counter: bottom-right
```

#### Dropdown/Select
```
Style:
- Height: 56dp
- Border: 1dp solid Border color
- Border Radius: sm (8dp)
- Padding: md (16dp)
- Font: Body Large
- Chevron icon: right side
- Background: Card Background

Dropdown Menu:
- Max Height: 300dp
- Scrollable
- Each option: 48dp height
- Selected: Primary Light background
- Ripple effect on items
```

### Cards

#### Standard Card
```
Style:
- Background: Card Background
- Border Radius: md (12dp)
- Padding: md (16dp)
- Shadow: Level 1
- Border: 1dp solid Border (dark mode only)

Hover (for web):
- Shadow: Level 2
- Transform: translateY(-2dp)
- Transition: 250ms
```

#### Featured Card
```
Style:
- Background: Gradient or Image
- Border Radius: lg (16dp)
- Padding: lg (24dp)
- Shadow: Level 2
- Text: White (with overlay)
```

### Chips/Tags

#### Status Chip
```
Style:
- Height: 24dp
- Padding: sm (8dp) horizontal
- Border Radius: xs (4dp)
- Font: Label Small, Medium
- No border

Colors:
- Success: Secondary bg, White text
- Warning: Warning bg, White text
- Error: Error bg, White text
- Info: Primary bg, White text
- Neutral: Background Tertiary, Text Primary
```

#### Filter Chip
```
Style:
- Height: 32dp
- Padding: md (12dp) horizontal
- Border Radius: full
- Font: Label Medium
- Border: 1dp solid

States:
- Unselected: Border color, Text Secondary
- Selected: Primary bg, White text
```

### Navigation

#### Bottom Navigation Bar
```
Style:
- Height: 64dp
- Background: Card Background
- Shadow: Level 2 (inverted)
- 5 items max
- Safe area padding (for iOS notch)

Item:
- Icon: 24dp
- Label: Label Medium
- Color Unselected: Text Tertiary
- Color Selected: Primary Brand
- Ripple Effect
- Active indicator: 2dp line above icon
```

#### Tab Bar
```
Style:
- Height: 48dp
- Background: Transparent
- Horizontal scroll if needed
- Divider: bottom border

Tab:
- Padding: md (16dp) horizontal
- Font: Label Large, Medium
- Color Unselected: Text Secondary
- Color Selected: Primary Brand
- Active indicator: 2dp line bottom (Primary)
- Min width: 90dp
```

### Modals/Dialogs

#### Alert Dialog
```
Style:
- Max Width: 320dp
- Background: Card Background
- Border Radius: lg (16dp)
- Padding: lg (24dp)
- Shadow: Level 4
- Centered on screen

Structure:
- Title: Title Large
- Message: Body Medium
- Actions: Row of buttons (right-aligned)
- Dismiss: Tap outside or back button
```

#### Bottom Sheet
```
Style:
- Background: Card Background
- Border Radius: xl (24dp) top corners
- Max Height: 90% screen
- Shadow: Level 4 (inverted)
- Drag handle: 32dp width, 4dp height, centered

Animation:
- Enter: Slide up (400ms)
- Exit: Slide down (300ms)
- Drag to dismiss enabled
```

### Lists

#### List Item
```
Style:
- Height: 56dp (1 line) / 72dp (2 lines) / 88dp (3 lines)
- Padding: md (16dp) horizontal
- Ripple effect
- Divider: between items (optional)

Structure:
- Leading icon/avatar: 40dp (left)
- Primary text: Body Large
- Secondary text: Body Medium, Text Secondary
- Trailing icon/action: 24dp (right)
- Spacing: sm (8dp) between elements
```

### Loading States

#### Skeleton Loader
```
Style:
- Background: Shimmer gradient
- Border Radius: match component
- Animation: Left to right shimmer (1.5s)

Light Mode:
- Base: #E0E0E0
- Shimmer: #F5F5F5

Dark Mode:
- Base: #2A2A2A
- Shimmer: #3A3A3A
```

#### Progress Indicator

**Circular (Indeterminate):**
```
Style:
- Size: 32dp / 24dp / 16dp
- Stroke: 3dp
- Color: Primary Brand
- Rotation: 360° (1.4s)
```

**Linear:**
```
Style:
- Height: 4dp
- Width: Full width
- Background: Primary Light 30%
- Progress: Primary Brand
- Border Radius: xs (4dp)
```

### Badges

#### Notification Badge
```
Style:
- Min Size: 16dp x 16dp
- Max Size: 24dp x 24dp
- Background: Error color
- Text: White
- Font: 10sp, Bold
- Border: 2dp solid background color
- Position: Top-right of icon (-4dp offset)
```

---

## Mock Data Structure

### Complete Data Schema

```json
{
  "user": {
    "id": "EMP-12345",
    "firstName": "John",
    "lastName": "Doe",
    "fullName": "John Doe",
    "email": "john.doe@company.com",
    "phone": "+1 (555) 123-4567",
    "avatar": "https://example.com/avatars/john-doe.jpg",
    "employeeId": "EMP-12345",
    "joinDate": "2023-03-15",
    "status": "active",
    "role": "Frontend Developer",
    "department": {
      "id": "dept-001",
      "name": "Engineering",
      "team": "Frontend Development"
    },
    "manager": {
      "id": "EMP-00123",
      "name": "Sarah Johnson",
      "email": "sarah.johnson@company.com",
      "avatar": "https://example.com/avatars/sarah-johnson.jpg"
    },
    "location": {
      "office": "San Francisco HQ",
      "floor": "3",
      "desk": "3-45A"
    },
    "emergencyContact": {
      "name": "Jane Doe",
      "relationship": "Spouse",
      "phone": "+1 (555) 987-6543"
    }
  },
  
  "dashboard": {
    "greeting": "Good Morning",
    "currentDate": "2026-01-15",
    "todayStatus": {
      "isCheckedIn": true,
      "checkInTime": "09:00 AM",
      "checkOutTime": null,
      "workDuration": "6h 30m",
      "location": "Office - Floor 3"
    },
    "monthlyStats": {
      "presentDays": 8,
      "totalWorkDays": 10,
      "leavesTaken": 5,
      "leavesAvailable": 21,
      "hoursWorked": 160,
      "requiredHours": 160,
      "performanceScore": 95
    },
    "quickActions": [
      {
        "id": "scan_qr",
        "label": "Scan QR",
        "icon": "📸",
        "action": "scan_attendance"
      },
      {
        "id": "apply_leave",
        "label": "Apply Leave",
        "icon": "📝",
        "action": "apply_leave"
      },
      {
        "id": "team_directory",
        "label": "Team",
        "icon": "👥",
        "action": "view_team"
      },
      {
        "id": "calendar",
        "label": "Calendar",
        "icon": "📅",
        "action": "view_calendar"
      }
    ],
    "recentAnnouncements": [
      {
        "id": 1,
        "title": "Company Holiday Announcement",
        "excerpt": "December 25 will be a paid holiday...",
        "timestamp": "2 hours ago",
        "icon": "🎉",
        "category": "Company"
      },
      {
        "id": 2,
        "title": "New Policy Update",
        "excerpt": "Please review the updated work from home policy...",
        "timestamp": "5 hours ago",
        "icon": "📋",
        "category": "HR"
      }
    ]
  },
  
  "attendance": {
    "today": {
      "date": "2026-01-15",
      "status": "checked_in",
      "checkIn": {
        "time": "09:00 AM",
        "timestamp": "2026-01-15T09:00:00Z",
        "location": "Office - Floor 3",
        "method": "QR Code"
      },
      "checkOut": null,
      "duration": "6h 30m",
      "breaks": [
        {
          "startTime": "12:00 PM",
          "endTime": "01:00 PM",
          "duration": "1h 00m",
          "type": "Lunch"
        }
      ]
    },
    "history": [
      {
        "id": 1,
        "date": "2026-01-14",
        "dateFormatted": "Jan 14, 2026",
        "dayName": "Tuesday",
        "checkIn": "09:00 AM",
        "checkOut": "06:00 PM",
        "duration": "8h 30m",
        "status": "complete",
        "location": "Office - Floor 3"
      },
      {
        "id": 2,
        "date": "2026-01-13",
        "dateFormatted": "Jan 13, 2026",
        "dayName": "Monday",
        "checkIn": "08:45 AM",
        "checkOut": "05:45 PM",
        "duration": "8h 30m",
        "status": "complete",
        "location": "Office - Floor 3"
      },
      {
        "id": 3,
        "date": "2026-01-12",
        "dateFormatted": "Jan 12, 2026",
        "dayName": "Sunday",
        "checkIn": null,
        "checkOut": null,
        "duration": "0h 00m",
        "status": "weekend",
        "location": null
      },
      {
        "id": 4,
        "date": "2026-01-11",
        "dateFormatted": "Jan 11, 2026",
        "dayName": "Saturday",
        "checkIn": null,
        "checkOut": null,
        "duration": "0h 00m",
        "status": "weekend",
        "location": null
      },
      {
        "id": 5,
        "date": "2026-01-10",
        "dateFormatted": "Jan 10, 2026",
        "dayName": "Friday",
        "checkIn": "09:15 AM",
        "checkOut": "06:00 PM",
        "duration": "8h 15m",
        "status": "complete",
        "location": "Office - Floor 3"
      }
    ],
    "monthlySummary": {
      "month": "January 2026",
      "totalWorkDays": 20,
      "presentDays": 18,
      "absentDays": 1,
      "halfDays": 1,
      "weekendDays": 9,
      "holidays": 1,
      "totalHours": 152,
      "requiredHours": 160,
      "averageCheckIn": "09:05 AM",
      "averageCheckOut": "06:05 PM"
    }
  },
  
  "leaves": {
    "balance": {
      "annual": {
        "type": "annual",
        "name": "Annual Leave",
        "icon": "🌴",
        "total": 15,
        "used": 3,
        "available": 12,
        "pending": 0
      },
      "sick": {
        "type": "sick",
        "name": "Sick Leave",
        "icon": "🤒",
        "total": 10,
        "used": 4,
        "available": 6,
        "pending": 0
      },
      "casual": {
        "type": "casual",
        "name": "Casual Leave",
        "icon": "🏠",
        "total": 5,
        "used": 2,
        "available": 3,
        "pending": 0
      }
    },
    "requests": [
      {
        "id": 1,
        "type": "annual",
        "typeName": "Annual Leave",
        "icon": "🌴",
        "startDate": "2026-01-20",
        "endDate": "2026-01-22",
        "dateDisplay": "Jan 20 - Jan 22, 2026",
        "days": 3,
        "reason": "Family vacation to Bali for my daughter's birthday celebration.",
        "status": "pending",
        "appliedDate": "2026-01-15",
        "appliedDateFormatted": "Jan 15, 2026",
        "approver": {
          "id": "EMP-00123",
          "name": "Sarah Johnson",
          "email": "sarah.johnson@company.com"
        },
        "attachments": []
      },
      {
        "id": 2,
        "type": "sick",
        "typeName": "Sick Leave",
        "icon": "🤒",
        "startDate": "2026-01-10",
        "endDate": "2026-01-10",
        "dateDisplay": "Jan 10, 2026",
        "days": 1,
        "reason": "Flu and fever. Visited Dr. Smith.",
        "status": "approved",
        "appliedDate": "2026-01-10",
        "appliedDateFormatted": "Jan 10, 2026",
        "approver": {
          "id": "EMP-00123",
          "name": "Sarah Johnson",
          "email": "sarah.johnson@company.com"
        },
        "approvedDate": "2026-01-10",
        "approvedDateFormatted": "Jan 10, 2026",
        "attachments": [
          {
            "id": 1,
            "name": "medical_certificate.pdf",
            "url": "https://example.com/files/medical_cert.pdf",
            "size": "245 KB",
            "type": "application/pdf"
          }
        ]
      }
    ]
  },
  
  "announcements": [
    {
      "id": 1,
      "title": "Company Holiday Announcement",
      "excerpt": "December 25 will be a paid holiday for all employees...",
      "content": "December 25 will be a paid holiday for all employees. Enjoy quality time with your family and loved ones. The office will remain closed and will reopen on December 26.\n\nPlease ensure all pending work is completed before the holiday. Emergency contacts will be shared via email.",
      "icon": "🎉",
      "category": "Company",
      "timestamp": "2 hours ago",
      "postedAt": "2026-01-15T10:00:00Z",
      "views": 234,
      "isFeatured": true,
      "author": {
        "id": "HR-001",
        "name": "HR Department",
        "avatar": "https://example.com/avatars/hr.jpg"
      },
      "attachments": [],
      "allowComments": true,
      "isPinned": true
    },
    {
      "id": 2,
      "title": "New Policy Update",
      "excerpt": "Please review the updated work from home policy...",
      "content": "Please review the updated work from home policy. All employees are now eligible for 2 days of remote work per week. Please coordinate with your manager and update your calendar.\n\nKey Changes:\n- Employees can work remotely up to 2 days/week\n- Manager approval required\n- Must be available during core hours (10 AM - 4 PM)\n- Stable internet connection required",
      "icon": "📋",
      "category": "HR",
      "timestamp": "5 hours ago",
      "postedAt": "2026-01-15T07:00:00Z",
      "views": 156,
      "isFeatured": false,
      "author": {
        "id": "EMP-00123",
        "name": "Sarah Johnson",
        "avatar": "https://example.com/avatars/sarah.jpg"
      },
      "attachments": [
        {
          "id": 1,
          "name": "WFH_Policy_2026.pdf",
          "url": "https://example.com/files/wfh_policy.pdf",
          "size": "245 KB",
          "type": "application/pdf"
        }
      ],
      "allowComments": true,
      "isPinned": false
    }
  ],
  
  "notifications": [
    {
      "id": 1,
      "title": "Leave Request Approved",
      "message": "Your sick leave for Jan 10 has been approved by Sarah Johnson",
      "timestamp": "2 hours ago",
      "postedAt": "2026-01-15T10:00:00Z",
      "isRead": false,
      "type": "leave_approved",
      "icon": "✅",
      "actionUrl": "/leaves/2"
    },
    {
      "id": 2,
      "title": "New Announcement",
      "message": "HR Department posted: Company Holiday Announcement",
      "timestamp": "3 hours ago",
      "postedAt": "2026-01-15T09:00:00Z",
      "isRead": false,
      "type": "announcement",
      "icon": "📢",
      "actionUrl": "/announcements/1"
    },
    {
      "id": 3,
      "title": "Check-in Reminder",
      "message": "Don't forget to check in for today",
      "timestamp": "1 day ago",
      "postedAt": "2026-01-14T09:00:00Z",
      "isRead": true,
      "type": "reminder",
      "icon": "⏰",
      "actionUrl": "/attendance"
    }
  ],
  
  "team": [
    {
      "id": "EMP-00123",
      "name": "Sarah Johnson",
      "role": "Engineering Manager",
      "department": "Engineering",
      "email": "sarah.johnson@company.com",
      "phone": "+1 (555) 111-2222",
      "avatar": "https://example.com/avatars/sarah.jpg",
      "location": "Office - Floor 3",
      "status": "available"
    },
    {
      "id": "EMP-12346",
      "name": "Michael Chen",
      "role": "Senior Frontend Developer",
      "department": "Engineering",
      "email": "michael.chen@company.com",
      "phone": "+1 (555) 222-3333",
      "avatar": "https://example.com/avatars/michael.jpg",
      "location": "Office - Floor 3",
      "status": "in_meeting"
    },
    {
      "id": "EMP-12347",
      "name": "Emily Rodriguez",
      "role": "Frontend Developer",
      "department": "Engineering",
      "email": "emily.rodriguez@company.com",
      "phone": "+1 (555) 333-4444",
      "avatar": "https://example.com/avatars/emily.jpg",
      "location": "Office - Floor 3",
      "status": "on_leave"
    }
  ]
}
```

---

## Responsive Guidelines

### Screen Size Breakpoints

```
Extra Small (xs): < 360dp
Small (sm): 360dp - 599dp (Most phones)
Medium (md): 600dp - 839dp (Large phones, small tablets)
Large (lg): 840dp - 1279dp (Tablets)
Extra Large (xl): >= 1280dp (Large tablets, desktops)
```

### Layout Adaptations

#### Portrait Mode (Default)
- Single column layout
- Full-width cards
- Bottom navigation visible
- Standard spacing

#### Landscape Mode
- Consider two-column layout for tablets
- Hide bottom navigation, use side navigation
- Reduce vertical spacing
- Utilize horizontal space

#### Tablet Adaptations (md, lg)
- Two-column grid for cards
- Side navigation instead of bottom
- Floating action buttons
- Larger typography scale
- Increased padding

### Safe Areas

#### Android
- Status Bar: 24dp (default)
- Navigation Bar: 48dp (3-button) / 0dp (gesture)
- Adaptive padding for notches/cutouts

#### iOS
- Status Bar: 44dp (standard) / 50dp (with Dynamic Island)
- Home Indicator: 34dp
- Safe Area Insets: Automatic handling
- Notch considerations

### Touch Targets

```
Minimum: 48dp x 48dp
Comfortable: 56dp x 56dp
Large: 64dp x 64dp
Spacing between targets: minimum 8dp
```

### Gesture Support

```
Swipe Left/Right: Navigate between screens, delete items
Swipe Down: Refresh content (pull-to-refresh)
Long Press: Show context menu, additional options
Pinch: Zoom (if applicable)
Double Tap: Quick actions (like/favorite)
```

### Performance Considerations

```
Image Loading:
- Use placeholder/skeleton while loading
- Progressive image loading
- Image caching
- Lazy loading for lists

Animations:
- 60 FPS target
- Hardware acceleration
- Reduce motion option (accessibility)
- Cancel animations on navigation

List Optimization:
- Virtualization for long lists
- Pagination/infinite scroll
- Item recycling
- Smooth scrolling
```

### Accessibility

```
Text:
- Scalable font sizes (support Dynamic Type)
- Minimum contrast ratio: 4.5:1 (normal text)
- Minimum contrast ratio: 3:1 (large text)
- Support for bold text setting

Touch:
- Minimum touch target: 48dp
- Proper focus indicators
- Keyboard navigation support

Screen Readers:
- Semantic HTML/proper labels
- Content descriptions for images
- Announce dynamic content changes
- Logical reading order

Visual:
- Support for high contrast mode
- No information conveyed by color alone
- Reduce motion option
- Clear focus states
```

### Dark Mode Implementation

```
Toggle:
- User preference saved locally
- System setting sync option
- Smooth transition animation (250ms)

Color Adaptation:
- Elevate surfaces with lighter colors
- Reduce shadows, use borders
- Desaturate colors slightly
- Increase surface contrast

Images:
- Consider dark mode variants
- Add overlay for better contrast
- Adjust opacity if needed
```

---

## Implementation Notes

### State Management
- Use modern state management (e.g., Kotlin Flow, StateFlow)
- Maintain app state across configuration changes
- Cache user session securely
- Handle offline scenarios gracefully

### Navigation
- Deep linking support for notifications
- Smooth transitions between screens
- Handle back navigation properly
- Maintain navigation history

### API Integration (When Implemented)
```kotlin
// Base URL
const val BASE_URL = "https://api.company.com/v1"

// Endpoints
/auth/login (POST)
/user/profile (GET, PUT)
/attendance/checkin (POST)
/attendance/checkout (POST)
/attendance/history (GET)
/leave/balance (GET)
/leave/requests (GET, POST)
/leave/requests/{id} (GET, PUT, DELETE)
/announcements (GET)
/announcements/{id} (GET)
/notifications (GET)
/team (GET)
```

### Security
- Store tokens securely (Keychain/Keystore)
- Encrypt sensitive data
- Certificate pinning for API calls
- Implement biometric authentication option
- Automatic session timeout
- Secure QR code validation

### Error Handling
- User-friendly error messages
- Retry mechanisms for network errors
- Offline mode indicators
- Graceful degradation
- Error logging for debugging

---

## Testing Devices

### Android
- Samsung Galaxy S24 (2026) - 6.2" 1080x2340
- Google Pixel 8 - 6.2" 1080x2400
- Samsung Galaxy A54 - 6.4" 1080x2340
- OnePlus 11 - 6.7" 1440x3216

### iOS
- iPhone 15 Pro Max - 6.7" 1290x2796
- iPhone 15 Pro - 6.1" 1179x2556
- iPhone 15 - 6.1" 1179x2556
- iPhone SE (3rd gen) - 4.7" 750x1334

### Tablets
- iPad Pro 12.9" - 2048x2732
- iPad Air - 1640x2360
- Samsung Galaxy Tab S9 - 1600x2560

---

## Design Checklist

### Before Development
- [ ] All screens designed
- [ ] All user flows mapped
- [ ] Color palette finalized
- [ ] Typography scale defined
- [ ] Component library created
- [ ] Dark mode variants designed
- [ ] Icon set prepared
- [ ] Loading states designed
- [ ] Error states designed
- [ ] Empty states designed

### During Development
- [ ] Match design specs exactly
- [ ] Test on multiple devices
- [ ] Verify dark mode
- [ ] Check accessibility
- [ ] Test animations
- [ ] Verify responsive behavior
- [ ] Test gestures
- [ ] Check performance

### Before Launch
- [ ] Cross-platform testing
- [ ] User acceptance testing
- [ ] Accessibility audit
- [ ] Performance testing
- [ ] Security audit
- [ ] App store assets prepared
- [ ] Documentation complete

---

## File Organization (Kotlin Multiplatform)

```
/app
  /src
    /commonMain
      /kotlin
        /com/company/hr
          /data
            /models
            /repository
            /remote
          /domain
            /usecases
          /presentation
            /screens
              /auth
              /home
              /attendance
              /leave
              /announcements
              /profile
            /components
            /theme
          /utils
    /androidMain
      /kotlin
      /res
    /iosMain
      /kotlin
```

---

## Additional Resources

### Design Assets
- App icon (1024x1024px)
- Splash screen assets
- Tab bar icons (SF Symbols for iOS, Material Icons for Android)
- Illustrations for empty states
- Error state illustrations

### Fonts
- SF Pro (iOS default)
- Roboto (Android default)
- System fallbacks

### Design Tools Export
- Figma file (if available)
- Style guide document
- Component specifications
- Asset export guidelines

---

## Version History

**v2.0 (Current)**
- Updated to 2026 design standards
- Material Design 3 compliance
- iOS 17+ design patterns
- Enhanced dark mode
- Improved accessibility
- Modern animations

---

## Contact & Support

For design questions or clarifications:
- Review this guideline thoroughly
- Check platform-specific guidelines (Material Design / Human Interface Guidelines)
- Refer to Figma file for detailed measurements
- Contact design team for edge cases

---

**End of Design Guideline**

This document should be used as the single source of truth for implementing the HR Mobile App. All designers and developers should refer to this guide to ensure consistency across the application.

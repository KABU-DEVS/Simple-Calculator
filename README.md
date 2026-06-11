# Simple Android Calculator Application 📱

A fully functional Android Calculator developed to perform basic arithmetic operations and advanced scientific functions. This project demonstrates proficiency in Android layouts (XML), Java logic, resource management, and collaborative software development as part of the Android Mobile Application Development Group Assignment.

## 🎯 Project Objectives
- Design intuitive user interfaces using **XML**.
- Implement core widgets: **EditText**, **TextView**, and **Button**.
- Handle complex **Event Handling** and arithmetic logic in **Java**.
- Efficiently organize resources using **strings.xml** and **colors.xml**.
- Demonstrate high-quality **Error Handling** (e.g., Division by Zero).

## ✨ Functional Requirements
This application meets all mandatory requirements and exceeds expectations with bonus features.

### 🛠️ Core Functionality (Mandatory)
- **Dual Input**: Two `EditText` fields for high-precision numerical input.
- **Basic Operations**: Dedicated buttons for Addition (`+`), Subtraction (`-`), Multiplication (`×`), and Division (`÷`).
- **Result Display**: A prominent `TextView` that updates with each calculation result.
- **Clear Function**: A specialized button to reset both `EditText` inputs and the result `TextView`.
- **Error Handling**: Comprehensive logic to prevent crashes during "Division by Zero" or invalid input, displaying clear error messages.

### 🌟 Bonus Features (+5 Marks)
- **Scientific Functions**: Includes Square Root (`√`), Percentage (`%`), Square (`x²`), Power (`x^y`), and Modulo (`MOD`).
- **Dark Mode Support**: Manual and system-wide theme switching for enhanced accessibility.
- **Custom App Icon**: A custom launcher icon that replaces the default Android symbol.
- **Landscape Support**: Optimized layout using `ScrollView` and `LinearLayout` for seamless rotation.
- **Calculator History**: A modern `RecyclerView` implementation to track past calculations with timestamps.

## 👥 Team Roles & Responsibilities

| Member | Role | Responsibilities |
| :--- | :--- | :--- |
| **JOSEPH NG'ANG'A** | **Project Lead** | Repository setup, GitHub management, and final code integration. |
| **AMBROSE TANUI** | **UI Designer** | Designing `activity_main.xml`, custom drawables, and `colors.xml`. |
| **TROXEL WONDERFUL** | **Logic Developer** | Implementing `MainActivity.java` and core calculation algorithms. |
| **SAMMY KIBET** | **Resource & QA** | Managing `strings.xml`, app icons, testing, and final report documentation. |

## 📂 Technical Implementation Details

- **Language**: Java 11
- **UI Components**: `LinearLayout` (Nested), `ScrollView`, `RecyclerView`.
- **Resources**: Centralized `strings.xml` for all text and `colors.xml` for the theme palette.
- **State Management**: `onSaveInstanceState` used to preserve results across configuration changes.
- **Logic**: Robust implementation with `try-catch` blocks for `NumberFormatException` and specific division-by-zero checks.

## 🚀 Usage Instructions

1. **Clone**: `git clone https://github.com/KABU-DEVS/Simple-Calculator.git`
2. **Open**: Open the project in **Android Studio Koala** or higher.
3. **Run**: Select an emulator/device and click **Run**.
4. **Calculate**: Enter numbers, select an operation, and view your result. Use **CLEAR** to start over.

## 📝 Testing Summary

| Test Case | Input 1 | Input 2 | Operation | Expected Result | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Addition | 10 | 5 | `+` | 15 | ✅ Pass |
| Subtraction | 10 | 5 | `-` | 5 | ✅ Pass |
| Multiplication| 10 | 5 | `×` | 50 | ✅ Pass |
| Division | 10 | 5 | `÷` | 2 | ✅ Pass |
| Div by Zero | 10 | 0 | `÷` | Cannot divide by zero| ✅ Pass |
| Invalid Input | [empty] | 5 | `+` | Please enter both numbers| ✅ Pass |

---
**Project Status:** ✅ Final Submission | **Course:** Android Mobile Application Development
*Developed with precision and collaborative effort by **KABU-DEVS**.*

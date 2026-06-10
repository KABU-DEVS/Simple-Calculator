# Simple Calculator 📱

A clean, professional, and intuitive Android calculator application designed for everyday arithmetic. Built with Java and the Android SDK, this app focuses on simplicity, reliability, and a smooth user experience.

## ✨ Features

- **Core Arithmetic Operations**: Supports Addition, Subtraction, Multiplication, and Division.
- **Smart Input Validation**: Prevents errors by ensuring valid numerical inputs and handling division by zero gracefully.
- **State Preservation**: Remembers your results even when you rotate the screen or switch between apps.
- **Clean UI/UX**: Features a card-based design with clear visual feedback for operators and results.
- **Precision Handling**: Formats large and small results with up to 10 decimal places for clarity without clutter.
- **One-Tap Clear**: Quickly reset your workspace with the dedicated clear button.

## 🚀 Getting Started

### Prerequisites

- Android Studio Koala | 2024.1.1 or higher
- Android SDK 34 (UpsideDownCake)
- JDK 17

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/KABU-DEVS/Simple-Calculator.git
   ```

2. **Open in Android Studio**
   - Launch Android Studio and select **Open**.
   - Navigate to the project folder and click **OK**.

3. **Sync and Build**
   - Wait for the Gradle sync to finish.
   - Click the **Run** button (green play icon) to deploy the app to an emulator or physical device.

## 👥 Contributors & Responsibilities

| Member | Name | Responsibility |
| :--- | :--- | :--- |
| 1 | **JOSEPH NG'ANG'A** | Project Lead + GitHub Management + Integration |
| 2 | **AMBROSE TANUI** | UI Design (`activity_main.xml`, colors) |
| 3 | **TROXEL WONDERFUL** | Calculator Logic (`MainActivity.java`) |
| 4 | **SAMMY KIBET** | Resources, Testing, Documentation, Screenshots, Report |

## 🛠️ Built With

- **Java**: Primary programming language for logic and state management.
- **XML**: Used for crafting a responsive and accessible user interface.
- **Material Design**: For consistent and modern UI components.
- **Gradle**: Build automation system.

## 📂 Project Structure

```text
app/src/main/
├── java/com/example/simplecalculator/
│   └── MainActivity.java       # Core application logic
└── res/
    ├── layout/
    │   └── activity_main.xml  # UI design and layout
    ├── values/
    │   ├── colors.xml         # Brand and UI color palette
    │   └── strings.xml        # Localized string resources
    └── drawable/              # Custom backgrounds and assets
```

## 📝 Usage

1. Enter your **First Number**.
2. Enter your **Second Number**.
3. Tap one of the operator buttons (`+`, `−`, `×`, `÷`) to see the result immediately.
4. Use the **CLEAR** button to wipe all inputs and start a new calculation.

## 🤝 Contributing

Contributions are welcome! If you find a bug or have a feature request, please open an issue or submit a pull request.


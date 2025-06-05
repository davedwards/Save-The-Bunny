![Screenshot 2025-06-05 200243](https://github.com/user-attachments/assets/e7da93e6-4820-4be5-9545-b4728c249e14)

# Save The Bunny
Save The Bunny is a simple and fun Android game where you control a bunny and help it jump over obstacles to score points. The game is built using Java and Android SDK, following modern Android development practices.

## Table of Contents
- [Features](#features)
- [Screenshots](#screenshots)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Gameplay](#gameplay)
- [Build & Run](#build--run)
- [Testing](#testing)
- [Dependencies](#dependencies)
- [Contributing](#contributing)
- [License](#license)

---

## Features
- Simple tap-to-jump gameplay
- Score tracking
- Game over and restart functionality
- Clean and minimal UI
- Responsive animations
- Written in Java using AndroidX libraries

---

## Screenshots
*(![Screenshot 2025-06-05 200253](https://github.com/user-attachments/assets/6138f691-5cbb-4ddd-81fb-e56d586bedc9))*
---

## Getting Started
### Prerequisites
- Android Studio (recommended)
- Android SDK (API Level 24+)
- Java 11

### Installation
1. **Clone the repository:**
   ```sh
   git clone <your-repo-url>
   cd SaveTheBunny
   ```
2. **Open in Android Studio:**
   - Select `Open an existing project`
   - Choose the `SaveTheBunny` directory
3. **Build the project:**
   - Let Gradle sync and download dependencies
4. **Run the app:**
   - Connect an Android device or start an emulator
   - Click the Run button in Android Studio.
---

## Project Structure
```
SaveTheBunny/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/savethebunny/
│   │   │   │   └── MainActivity.java
│   │   │   ├── res/
│   │   │   │   ├── layout/activity_main.xml
│   │   │   │   ├── drawable/ic_bunny.xml, ic_obstacle.xml, ...
│   │   │   │   └── values/strings.xml
│   │   │   └── AndroidManifest.xml
│   │   ├── test/
│   │   │   └── java/com/example/savethebunny/ExampleUnitTest.java
│   │   └── androidTest/
│   │       └── java/com/example/savethebunny/ExampleInstrumentedTest.java
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

---

## Gameplay
- Tap anywhere in the game area to make the bunny jump.
- Avoid colliding with obstacles that move from right to left.
- Each time you successfully avoid an obstacle, your score increases.
- If the bunny collides with an obstacle, the game ends and you can restart.
---

## Build & Run
- The project uses Gradle for build automation.
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 35
- Java Version: 11

**To build and run:**
- Use Android Studio's built-in tools, or run:
  ```sh
  ./gradlew assembleDebug
  ```
---

## Testing
- **Unit Tests:** Located in `app/src/test/java/com/example/savethebunny/ExampleUnitTest.java`
- **Instrumentation Tests:** Located in `app/src/androidTest/java/com/example/savethebunny/ExampleInstrumentedTest.java`
- Run tests using Android Studio's test runner or via command line:
  ```sh
  ./gradlew test
  ./gradlew connectedAndroidTest
  ```
  ---

## Dependencies
- AndroidX AppCompat
- Material Components
- ConstraintLayout
- JUnit (for unit testing)
- Espresso (for UI testing)
All dependencies are managed via Gradle and defined in `build.gradle.kts`.
---

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request.
---

## License
*( license here: MIT, Apache 2.0, etc.)*
---

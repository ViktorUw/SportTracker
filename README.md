# SportTracker

An Android app for logging workouts. Users create an account, pick an exercise from the catalog, save their result for a chosen day and review their exercise history.

## Features

- **Registration and login** with user accounts stored in a local database
- **Exercise catalog** split into *indoor* (gym) and *outdoor* exercises, e.g. running, plank, squats, deadlift, dumbbell press
- **Exercise details** with a picture and description
- **Logging results**: enter a result and pick a date with a date picker
- **Completed exercises**: history of all saved results, with the option to delete an entry
- **Profile**: view and edit user data (birth date, weight) and log out
- Bottom navigation: *Exercise list*, *Completed exercises*, *Profile*

## Tech stack

- **Kotlin**
- **Room** (users, exercises, exercise results) with coroutines
- **MVVM**: `ViewModel` + `LiveData` + repositories
- **Navigation Component** with Safe Args and `BottomNavigationView`
- `SharedPreferences` for the logged-in session
- Min SDK 28, target SDK 35

## Project structure

```
app/src/main/java/com/example/sporttracker/
├── database/      # AppDatabase (with initial exercises), DAOs
├── models/        # User, Exercise, ExerciseResult + their ViewModels
├── repository/    # UserRepository, ExerciseRepository
├── ui/
│   ├── login/, register/       # Authentication
│   ├── ExercisesFragment.kt    # Exercise catalog
│   ├── ExerciseDetailFragment.kt
│   ├── PerformExerciseFragment.kt
│   ├── completedExercises/     # History of results
│   └── UserDataFragment.kt     # Profile
└── SharedPreferencesManager.kt
```

## Getting started

1. Clone the repository:
   ```bash
   git clone https://github.com/ViktorUw/SportTracker.git
   ```
2. Open the project in **Android Studio** and let Gradle sync.
3. Run the app on an emulator or a device with Android 9.0 (API 28) or newer.
4. Register a new account, then log in.

> The interface is in Polish. This is a university project: passwords are stored in plain text in the local database.

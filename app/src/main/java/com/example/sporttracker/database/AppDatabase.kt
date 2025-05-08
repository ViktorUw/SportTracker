package com.example.sporttracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.sporttracker.models.user.User
import com.example.sporttracker.models.exercise.Exercise
import com.example.sporttracker.models.exerciseResult.ExerciseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Database(entities = [User::class, Exercise::class, ExerciseResult::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun exerciseResultDao(): ExerciseResultDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigrationFrom()
                    .build()
                INSTANCE = instance

                GlobalScope.launch(Dispatchers.IO){
                    val exerciseDao = instance.exerciseDao()
                    prepopulateDatabase(exerciseDao)
                }
                instance
            }
        }

        private suspend fun prepopulateDatabase(exerciseDao: ExerciseDao) {
            if (exerciseDao.getExerciseCount() == 0) {
                val exercises = listOf(
                    Exercise(name = "Bieganie", description = "Bieganie to świetne cardio", imageUrl = "running", type = "outdoor"),
                    Exercise(name = "Plank", description = "Ćwiczenie na wzmocnienie core", imageUrl = "plank", type = "outdoor"),
                    Exercise(name = "Przysiady", description = "Dobre na nogi", imageUrl = "przysiady", type = "outdoor"),
                    Exercise(name = "Podskoki", description = "Świetne na rozgrzewkę", imageUrl = "podskoki", type = "outdoor"),
                    Exercise(name = "Wyciskanie hantli", description = "Ćwiczenie na klatkę piersiową", imageUrl = "wyciskanie", type = "indoor"),
                    Exercise(name = "Martwy ciąg", description = "Ćwiczenie на plecy", imageUrl = "martwy_ciag", type = "indoor"),
                    Exercise(name = "Arnoldki", description = "Ćwiczenie на barki", imageUrl = "arnoldki", type = "indoor")
                )
                exercises.forEach { exerciseDao.insertExercise(it) }
            }
        }
    }
}
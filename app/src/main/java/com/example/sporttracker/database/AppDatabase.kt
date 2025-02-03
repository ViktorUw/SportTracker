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
                ).build()
                INSTANCE = instance

//                GlobalScope.launch(Dispatchers.IO){
//                    val exerciseDao = instance.exerciseDao()
//                    prepopulateDatabase(exerciseDao)
//                }
                instance
            }
        }

        private suspend fun prepopulateDatabase(exerciseDao: ExerciseDao) {
            val exercises = listOf(
                Exercise(name = "Bieganie", description = "Bieganie to świetne cardio", imageUrl = "", type = "outdoor"),
                Exercise(name = "Plank", description = "Ćwiczenie na wzmocnienie core", imageUrl = "", type = "outdoor"),
                Exercise(name = "Przysiady", description = "Dobre na nogi", imageUrl = "", type = "outdoor"),
                Exercise(name = "Podskoki", description = "Świetne na rozgrzewkę", imageUrl = "", type = "outdoor"),
                Exercise(name = "Wyciskanie hantli", description = "Ćwiczenie na klatkę piersiową", imageUrl = "", type = "indoor"),
                Exercise(name = "Martwy ciąg", description = "Ćwiczenie na plecy", imageUrl = "", type = "indoor"),
                Exercise(name = "Arnoldki", description = "Ćwiczenie na barki", imageUrl = "", type = "indoor")
            )
            exercises.forEach { exerciseDao.insertExercise(it) }
        }
    }
}
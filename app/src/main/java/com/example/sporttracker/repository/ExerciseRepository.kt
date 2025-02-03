package com.example.sporttracker.repository

import androidx.lifecycle.LiveData
import com.example.sporttracker.database.ExerciseDao
import com.example.sporttracker.models.exercise.Exercise


class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    fun getAllExercises(): LiveData<List<Exercise>> {
        return exerciseDao.getAllExercises()
    }

    fun getExercisesByType(filter: String): LiveData<List<Exercise>> {
        return exerciseDao.getExercisesByType(filter)
    }

    suspend fun insertExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }
}
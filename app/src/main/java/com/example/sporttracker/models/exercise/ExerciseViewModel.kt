package com.example.sporttracker.models.exercise

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sporttracker.database.AppDatabase
import com.example.sporttracker.repository.ExerciseRepository
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExerciseRepository
    val allExercises: LiveData<List<Exercise>>

    init {
        val exerciseDao = AppDatabase.getDatabase(application).exerciseDao()
        repository = ExerciseRepository(exerciseDao)
        allExercises = repository.getAllExercises()
    }

    fun getExercisesByType(filter: String): LiveData<List<Exercise>> {
        return repository.getExercisesByType(filter)
    }

    fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            repository.insertExercise(exercise)
        }
    }
}

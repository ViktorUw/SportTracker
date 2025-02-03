package com.example.sporttracker.models.exerciseResult

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sporttracker.database.AppDatabase
import kotlinx.coroutines.launch

class ExerciseResultViewModel(application: Application) : AndroidViewModel(application) {
    private val exerciseResultDao = AppDatabase.getDatabase(application).exerciseResultDao()

    fun getResults(userId: Int): LiveData<List<ExerciseResult>> {
        return exerciseResultDao.getExerciseResult(userId)
    }

    suspend fun saveResult(exerciseResult: ExerciseResult){
        return exerciseResultDao.insertExerciseResult(exerciseResult)
    }
}

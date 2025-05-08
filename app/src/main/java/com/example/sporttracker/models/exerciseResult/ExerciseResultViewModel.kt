package com.example.sporttracker.models.exerciseResult

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sporttracker.database.AppDatabase
import kotlinx.coroutines.launch
import java.text.Format
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExerciseResultViewModel(application: Application) : AndroidViewModel(application) {
    private val exerciseResultDao = AppDatabase.getDatabase(application).exerciseResultDao()

    fun getResults(userId: Int): LiveData<List<ExerciseResult>> {
        return exerciseResultDao.getExerciseResult(userId)
    }

    suspend fun saveResult(exerciseResult: ExerciseResult){
        return exerciseResultDao.insertExerciseResult(exerciseResult)
    }

    fun getCompletedExercises(userId: Int): LiveData<List<CompletedExercise>> {
        return exerciseResultDao.getCompletedExercises(userId)
    }

    fun getTodatExercieCount(userId: Int): LiveData<Int>{
        val today = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        return exerciseResultDao.getDailyExercisesCount(userId, today)
    }


}

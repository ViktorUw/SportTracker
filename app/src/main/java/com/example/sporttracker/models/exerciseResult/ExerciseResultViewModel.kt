package com.example.sporttracker.models.exerciseResult

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sporttracker.database.AppDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExerciseResultViewModel(application: Application) : AndroidViewModel(application) {
    private val exerciseResultDao = AppDatabase.getDatabase(application).exerciseResultDao()

    suspend fun saveResult(exerciseResult: ExerciseResult) {
        return exerciseResultDao.insertExerciseResult(exerciseResult)
    }

    fun getCompletedExercises(userId: Int): LiveData<List<CompletedExercise>> {
        return exerciseResultDao.getCompletedExercises(userId)
    }

    fun getTodayExercieCount(userId: Int): LiveData<Int> {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return exerciseResultDao.getDailyExercisesCount(userId, today)
    }

    fun deleteByFields(userId: Int, exerciseId: Int, date: String, result: String) {
        viewModelScope.launch {
            exerciseResultDao.deleteByFields(userId, exerciseId, date, result)
        }
    }

    fun getTotalExerciseCount(userId: Int): LiveData<Int> {
        return exerciseResultDao.getTotalExerciseCount(userId)
    }

    fun getMonthlyExerciseCount(userId: Int): LiveData<Int> {
        return exerciseResultDao.getMonthlyExerciseCount(userId)
    }
    fun getPreviousMounthExerciseCount(userId: Int): LiveData<Int>{
        return  exerciseResultDao.getPreviousMounthExerciseCount(userId)
    }


}

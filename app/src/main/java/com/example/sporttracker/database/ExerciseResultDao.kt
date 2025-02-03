package com.example.sporttracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.sporttracker.models.ExerciseResultWithExercise
import com.example.sporttracker.models.exerciseResult.ExerciseResult

@Dao
interface ExerciseResultDao {

    @Insert
    suspend fun insertExerciseResult(result: ExerciseResult)

    @Transaction
    @Query("SELECT * FROM exercise_result WHERE userId = :userId")
    fun getExerciseResult(userId: Int): LiveData<List<ExerciseResult>>
}

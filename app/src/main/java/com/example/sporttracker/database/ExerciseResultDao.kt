package com.example.sporttracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.sporttracker.models.exerciseResult.CompletedExercise
import com.example.sporttracker.models.exerciseResult.ExerciseResult

@Dao
interface ExerciseResultDao {

    @Insert
    suspend fun insertExerciseResult(result: ExerciseResult)

    @Transaction
    @Query("SELECT * FROM exercise_result WHERE userId = :userId")
    fun getExerciseResult(userId: Int): LiveData<List<ExerciseResult>>

    @Query("""
        SELECT e.id AS exerciseId, e.name AS exerciseName, er.result, er.date
        FROM exercise_result er
        INNER JOIN exercises e ON er.exerciseId = e.id
        WHERE er.userId = :userId
        ORDER BY er.date DESC
    """)
    fun getCompletedExercises(userId: Int): LiveData<List<CompletedExercise>>


    @Query("""
    SELECT COUNT(*) FROM exercise_result 
    WHERE userId = :userId AND date = :today
""")
    fun getDailyExercisesCount(userId: Int, today: String): LiveData<Int>

}

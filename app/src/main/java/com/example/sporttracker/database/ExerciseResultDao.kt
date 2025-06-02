package com.example.sporttracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.sporttracker.models.exerciseResult.CompletedExercise
import com.example.sporttracker.models.exerciseResult.ExerciseResult

@Dao
interface ExerciseResultDao {

    @Insert
    suspend fun insertExerciseResult(result: ExerciseResult)

    @Delete
    suspend fun deleteExerciseResult(result: ExerciseResult)

    @Query("DELETE FROM exercise_result WHERE id = :resultId")
    suspend fun deleteExerciseResultById(resultId: Int)

    @Transaction
    @Query("SELECT * FROM exercise_result WHERE userId = :userId")
    fun getExerciseResult(userId: Int): LiveData<List<ExerciseResult>>

    @Query("DELETE FROM exercise_result WHERE userId = :userId AND exerciseId = :exerciseId AND date = :date AND result = :result ")
    suspend fun deleteByFields(userId: Int, exerciseId: Int, date: String, result: String)


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

    @Query("""
    SELECT COUNT(*) FROM exercise_result WHERE userId = :userId
""")
    fun getTotalExerciseCount(userId: Int): LiveData<Int>

    @Query("""
    SELECT COUNT(*) FROM exercise_result
    WHERE userId = :userId AND strftime('%m', date) = strftime('%m', 'now')
        AND strftime('%Y', date) = strftime('%Y', 'now')
""")
    fun getMonthlyExerciseCount(userId: Int): LiveData<Int>

    @Query("""
    SELECT COUNT(*) FROM exercise_result
    WHERE userId = :userId 
    AND strftime('%Y-%m', date) = strftime('%Y-%m', date('now', '-1 month'))
    """)
    fun getPreviousMounthExerciseCount(userId: Int) : LiveData<Int>

}

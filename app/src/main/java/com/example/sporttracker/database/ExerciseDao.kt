package com.example.sporttracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sporttracker.models.exercise.Exercise

@Dao
interface ExerciseDao {

    @Query("SELECT COUNT(*) FROM exercises")
    suspend fun getExerciseCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercise(exercise: Exercise)



    @Query("SELECT * FROM exercises")
    fun getAllExercises(): LiveData<List<Exercise>>

    
    @Query("SELECT * FROM exercises WHERE type = :filter")
    fun getExercisesByType(filter: String): LiveData<List<Exercise>>
}

package com.example.sporttracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sporttracker.models.exercise.Exercise

@Dao
interface ExerciseDao {

    // Вставка упражнения в базу данных
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercise(exercise: Exercise)

    // Получение всех упражнений
    @Query("SELECT * FROM exercises")
    fun getAllExercises(): LiveData<List<Exercise>>

    // Получение упражнений по типу
    @Query("SELECT * FROM exercises WHERE type = :filter")
    fun getExercisesByType(filter: String): LiveData<List<Exercise>>
}

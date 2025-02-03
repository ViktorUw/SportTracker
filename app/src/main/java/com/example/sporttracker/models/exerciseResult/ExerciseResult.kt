package com.example.sporttracker.models.exerciseResult

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "exercise_result")
data class ExerciseResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val exerciseId: Int,
    val result: String,
    val date: String = Date().toString()
)

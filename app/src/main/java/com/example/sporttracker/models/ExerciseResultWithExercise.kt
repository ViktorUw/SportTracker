package com.example.sporttracker.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.sporttracker.models.exercise.Exercise
import com.example.sporttracker.models.exerciseResult.ExerciseResult

data class ExerciseResultWithExercise(
    @Embedded val exerciseResult: ExerciseResult,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "id"
    )
    val exercise: Exercise
)

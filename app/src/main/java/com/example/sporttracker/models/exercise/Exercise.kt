package com.example.sporttracker.models.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")

data class Exercise(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val name: String,
    val description: String,
    val imageUrl: String,
    val type: String

)
package com.example.sporttracker.ui.completed_exercises

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sporttracker.R
import com.example.sporttracker.models.ExerciseResultWithExercise
import com.example.sporttracker.models.exerciseResult.ExerciseResult
import java.text.SimpleDateFormat
import java.util.Locale

class CompletedExercisesAdapter(private var exercises: List<ExerciseResultWithExercise>) :
    RecyclerView.Adapter<CompletedExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseName: TextView = view.findViewById(R.id.textExerciseName)
        val exerciseResult: TextView = view.findViewById(R.id.editTextExerciseResult)
        val exerciseDate: TextView = view.findViewById(R.id.textExerciseDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_completed_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exerciseResultWithExercise = exercises[position]


        holder.exerciseName.text = "Ćwiczenia: ${exerciseResultWithExercise.exercise.name}"
        holder.exerciseResult.text = "Rezultat: ${exerciseResultWithExercise.exerciseResult.result}"

        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.exerciseDate.text = "Data: ${exerciseResultWithExercise.exerciseResult.date}"
    }

    override fun getItemCount() = exercises.size

    fun updateData(newExercises: List<ExerciseResultWithExercise>) {
        exercises = newExercises
        notifyDataSetChanged()
        Log.d("CompletedExercisesAdapter", "Adapter updated with ${newExercises.size} items")
    }
}

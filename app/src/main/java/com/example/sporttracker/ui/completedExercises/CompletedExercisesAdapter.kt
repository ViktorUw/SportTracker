package com.example.sporttracker.ui.completed_exercises

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sporttracker.R
import com.example.sporttracker.models.exerciseResult.CompletedExercise

class CompletedExercisesAdapter(
    private var exercises: List<CompletedExercise>,
    private val onDeleteClick: (CompletedExercise) -> Unit
) : RecyclerView.Adapter<CompletedExercisesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseName: TextView = view.findViewById(R.id.textExerciseName)
        val exerciseResult: TextView = view.findViewById(R.id.textExerciseResult)
        val exerciseDate: TextView = view.findViewById(R.id.textExerciseDate)
        val deleteBtn: Button = view.findViewById(R.id.buttonDel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_completed_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]

        holder.exerciseName.text = "Ćwiczenia: ${exercise.exerciseName}"
        holder.exerciseResult.text = "Rezultat: ${exercise.result}"
        holder.exerciseDate.text = "Data: ${exercise.date}"

        holder.deleteBtn.setOnClickListener {
            onDeleteClick(exercise)
        }
    }


    override fun getItemCount() = exercises.size

    fun updateData(newExercises: List<CompletedExercise>) {
        exercises = newExercises
        notifyDataSetChanged()
    }
}
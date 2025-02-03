package com.example.sporttracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sporttracker.R
import com.example.sporttracker.models.exercise.Exercise
import com.example.sporttracker.models.exercise.ExerciseViewModel

class ExerciseDetailFragment : Fragment() {

    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var exercise: Exercise

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exerciseName = arguments?.getString("exercise_name") ?: ""
        exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)

        exerciseViewModel.allExercises.observe(viewLifecycleOwner, { exercises ->
            exercise = exercises.find { it.name == exerciseName } ?: return@observe

            view.findViewById<TextView>(R.id.textExerciseName).text = exercise.name
            view.findViewById<TextView>(R.id.textExerciseDescription).text = exercise.description
            // Здесь можно загрузить картинку, например, через Glide или Picasso
            // Glide.with(this).load(exercise.imageUrl).into(view.findViewById<ImageView>(R.id.imageExercise))
        })

        val buttonPerformExercise = view.findViewById<Button>(R.id.buttonPerformExercise)
        buttonPerformExercise.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("exercise_name", exercise.name)
            bundle.putInt("exerciseId", exercise.id)

            findNavController().navigate(R.id.action_exerciseDetailFragment_to_performExerciseFragment, bundle)
        }
    }
}
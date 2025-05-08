package com.example.sporttracker.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sporttracker.R
import com.example.sporttracker.models.exercise.Exercise
import com.example.sporttracker.models.exercise.ExerciseAdapter
import com.example.sporttracker.models.exercise.ExerciseViewModel

class ExercisesFragment : Fragment() {

    private val exerciseViewModel: ExerciseViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter

    private var selectedCategory: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercises, container, false)

        selectedCategory = arguments?.getString("location")

        recyclerView = view.findViewById(R.id.recyclerViewExercises)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        filterExercises(selectedCategory)

        return view
    }

    private fun filterExercises(category: String?) {
        if (category.isNullOrEmpty() || category == "All") {
            exerciseViewModel.allExercises.observe(viewLifecycleOwner) { exercises ->
                adapter = ExerciseAdapter(exercises) { exercise ->
                    navigateToExerciseDetail(exercise)
                }
                recyclerView.adapter = adapter
            }
        } else {
            exerciseViewModel.getExercisesByType(category).observe(viewLifecycleOwner) { exercises ->
                adapter = ExerciseAdapter(exercises) { exercise ->
                    navigateToExerciseDetail(exercise)
                }
                recyclerView.adapter = adapter
            }
        }
    }

    private fun navigateToExerciseDetail(exercise: Exercise) {
        val bundle = Bundle().apply {
            putString("exercise_name", exercise.name)
            putString("imageUrl", exercise.imageUrl)
        }

        findNavController().navigate(R.id.action_exercisesFragment_to_exerciseDetailFragment, bundle)
    }
}

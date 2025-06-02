package com.example.sporttracker.ui.completed_exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sporttracker.databinding.FragmentCompletedExercisesBinding
import com.example.sporttracker.models.exerciseResult.ExerciseResultViewModel
import com.example.sporttracker.ui.WelcomeFragment

class CompletedExercisesFragment : Fragment() {

    private lateinit var binding: FragmentCompletedExercisesBinding
    private val exerciseResultViewModel: ExerciseResultViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CompletedExercisesAdapter
    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompletedExercisesBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.completedExercisesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        userId = WelcomeFragment.GlobalVariables.userId


        adapter = CompletedExercisesAdapter(emptyList()) { completedExercise ->
            exerciseResultViewModel.deleteByFields(
                userId = userId,
                exerciseId = completedExercise.exerciseId,
                date = completedExercise.date,
                result = completedExercise.result

            )
        }
        recyclerView.adapter = adapter


        exerciseResultViewModel.getCompletedExercises(userId).observe(viewLifecycleOwner) { results ->
            adapter.updateData(results)
        }

        exerciseResultViewModel.getTodayExercieCount(userId).observe(viewLifecycleOwner) { count ->
            binding.dailyExerciseCount.text = "Dzisiaj wykonałeś $count ćwiczeń"
        }

        return view
    }
}

package com.example.sporttracker.ui.completed_exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sporttracker.R
import com.example.sporttracker.databinding.FragmentCompletedExercisesBinding
import com.example.sporttracker.models.exerciseResult.ExerciseResultViewModel
import com.example.sporttracker.ui.WelcomeFragment

class CompletedExercisesFragment : Fragment() {

    private lateinit var binding: FragmentCompletedExercisesBinding
    private val exerciseResultViewModel: ExerciseResultViewModel by viewModels()
    private lateinit var listView: ListView
    private var userId: Int = WelcomeFragment.GlobalVariables.userId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompletedExercisesBinding.inflate(inflater)
        val view = binding.root

        listView = view.findViewById(R.id.completedExercisesListView)


        exerciseResultViewModel.getResults(userId).observe(viewLifecycleOwner) { results ->

            val resultStrings = results.map { "${it.id} - ${it.result} - ${it.date}" }


            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, resultStrings)
            listView.adapter = adapter
        }

        return view
    }
}

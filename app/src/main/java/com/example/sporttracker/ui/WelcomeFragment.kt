package com.example.sporttracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sporttracker.R
import com.example.sporttracker.databinding.FragmentWelcomeBinding
import com.example.sporttracker.models.exercise.ExerciseViewModel
import com.example.sporttracker.models.user.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class WelcomeFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var username: String


    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var binding: FragmentWelcomeBinding

    object GlobalVariables {
        var userId: Int = -1 // Значення за замовчуванням
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java) // Initialize the exerciseViewModel

        val bottomNavigation = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNavigation.visibility = View.VISIBLE

        username = arguments?.getString("username") ?: "User"
        GlobalVariables.userId = arguments?.getInt("userId") ?: -1



        val welcomeText = view.findViewById<TextView>(R.id.textWelcome)
        welcomeText.text = "Witaj, $username"

        val gymButton = view.findViewById<Button>(R.id.buttonGym)
        val outdoorButton = view.findViewById<Button>(R.id.buttonOutdoor)
        val AllExercisesButton = view.findViewById<Button>(R.id.AllExercisesButton)

        gymButton.setOnClickListener {
            navigateToExercises("indoor")
        }

        outdoorButton.setOnClickListener {
            navigateToExercises("outdoor")
        }

        AllExercisesButton.setOnClickListener{
            navigateToExercises("All")
        }

    }

    private fun navigateToExercises(location: String) {
        val bundle = Bundle()
        bundle.putString("location", location)
        findNavController().navigate(R.id.action_welcomeFragment_to_exercisesFragment, bundle)
    }
}
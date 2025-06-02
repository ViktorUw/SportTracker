package com.example.sporttracker.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sporttracker.R
import com.example.sporttracker.models.user.UserViewModel
import com.example.sporttracker.models.exerciseResult.ExerciseResultViewModel
import com.example.sporttracker.ui.WelcomeFragment.GlobalVariables.userId
import com.example.sporttracker.utils.SharedPreferencesManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UserDataFragment : Fragment() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextBirthdate: EditText
    private lateinit var editTextWeight: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonLogout: Button
    private lateinit var sharedPrefs: SharedPreferencesManager

    private lateinit var userViewModel: UserViewModel
    private lateinit var exerciseResultViewModel: ExerciseResultViewModel

    private lateinit var textMonthlyExerciseCount: TextView
    private lateinit var textTotalExerciseCount: TextView
    private lateinit var textPreviousMonthlyExerciseCount: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_data, container, false)

        editTextUsername = view.findViewById(R.id.editTextUsername)
        editTextBirthdate = view.findViewById(R.id.editTextBirthdate)
        editTextWeight = view.findViewById(R.id.editTextWeight)
        buttonSave = view.findViewById(R.id.buttonSave)
        buttonLogout = view.findViewById(R.id.buttonLogout)

        textMonthlyExerciseCount = view.findViewById(R.id.textMonthlyExerciseCount)
        textTotalExerciseCount = view.findViewById(R.id.textTotalExerciseCount)
        textPreviousMonthlyExerciseCount = view.findViewById(R.id.textPreviousMonthlyExerciseCount)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        exerciseResultViewModel = ViewModelProvider(this)[ExerciseResultViewModel::class.java]

        sharedPrefs = SharedPreferencesManager(requireContext())

        loadUserData()

        editTextBirthdate.setOnClickListener {
            showDatePicker()
        }

        buttonSave.setOnClickListener {
            saveUserData()
        }

        buttonLogout.setOnClickListener {
            logoutUser()
        }

        userId = WelcomeFragment.GlobalVariables.userId

        exerciseResultViewModel.getTotalExerciseCount(userId).observe(viewLifecycleOwner) { count ->
            textTotalExerciseCount.text = "Łącznie: $count ćwiczeń"
        }

        exerciseResultViewModel.getMonthlyExerciseCount(userId).observe(viewLifecycleOwner) { count ->
            textMonthlyExerciseCount.text = "W tym miesiącu: $count ćwiczeń"
        }

        exerciseResultViewModel.getPreviousMounthExerciseCount(userId).observe(viewLifecycleOwner) {count ->
            textPreviousMonthlyExerciseCount.text = "W zeszłym miesiącu wykonałeś: $count ćwiczeń"
        }


        return view
    }

    private fun loadUserData() {
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                editTextUsername.setText(it.username)
                editTextBirthdate.setText(it.birthdate)
                editTextWeight.setText(it.weight.toString())
            }
        }
        userViewModel.loadUser()
    }

    private fun saveUserData() {
        val username = editTextUsername.text.toString()
        val birthdate = editTextBirthdate.text.toString()
        val weight = editTextWeight.text.toString().toFloatOrNull() ?: 0f

        if (username.isEmpty() || birthdate.isEmpty()) {
            Toast.makeText(requireContext(), "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show()
            return
        }

        userViewModel.user.value?.let { existingUser ->
            val updatedUser = existingUser.copy(
                username = username,
                birthdate = birthdate,
                weight = weight
            )

            userViewModel.updateUser(updatedUser)
            userViewModel.setUser(updatedUser)
            Toast.makeText(requireContext(), "Dane zaktualizowane!", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(requireContext(), "Nie znależiono użytkownika!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                editTextBirthdate.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun logoutUser() {

        sharedPrefs.clearUserData()
        Toast.makeText(requireContext(), "Nastąpiło Wylogowanie!", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_userDataFragment_to_loginFragment)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.GONE
    }
}

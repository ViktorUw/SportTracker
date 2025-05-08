package com.example.sporttracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sporttracker.models.user.User
import com.example.sporttracker.models.user.UserViewModel
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
    private lateinit var sharedPrefs: SharedPreferencesManager

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_data, container, false)

        val buttonLogout: Button = view.findViewById(R.id.buttonLogout)

        editTextUsername = view.findViewById(R.id.editTextUsername)
        editTextBirthdate = view.findViewById(R.id.editTextBirthdate)
        editTextWeight = view.findViewById(R.id.editTextWeight)
        buttonSave = view.findViewById(R.id.buttonSave)


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

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

    fun showDatePicker() {
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
        // Очищаємо дані користувача
        sharedPrefs.clearUserData()

        // Переходимо на екран входу
        findNavController().navigate(R.id.action_userDataFragment_to_loginFragment)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavView).visibility = View.GONE
        // Закриваємо поточний фрагмент (якщо потрібно)
//        requireActivity().finish()
    }
}

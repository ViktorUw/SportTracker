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
import com.example.sporttracker.models.user.User
import com.example.sporttracker.models.user.UserViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UserDataFragment : Fragment() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextBirthdate: EditText
    private lateinit var editTextWeight: EditText
    private lateinit var buttonSave: Button

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_data, container, false)

        editTextUsername = view.findViewById(R.id.editTextUsername)
        editTextBirthdate = view.findViewById(R.id.editTextBirthdate)
        editTextWeight = view.findViewById(R.id.editTextWeight)
        buttonSave = view.findViewById(R.id.buttonSave)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        loadUserData()

        editTextBirthdate.setOnClickListener {
            showDatePicker()
        }

        buttonSave.setOnClickListener {
            saveUserData()
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
            Toast.makeText(requireContext(), "Заповніть всі поля", Toast.LENGTH_SHORT).show()
            return
        }

        userViewModel.user.value?.let { existingUser ->
            val updatedUser = existingUser.copy(
                username = username,
                birthdate = birthdate,
                weight = weight
            )

            userViewModel.updateUser(updatedUser)
            userViewModel.setUser(updatedUser) // 🔥 Обновляем LiveData в UI
            Toast.makeText(requireContext(), "Дані збережено!", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(requireContext(), "Помилка: користувач не знайдений!", Toast.LENGTH_SHORT).show()
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
}

package com.example.sporttracker.ui.register

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
import com.example.sporttracker.models.user.User
import com.example.sporttracker.R
import com.example.sporttracker.models.user.UserViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class RegisterFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val usernameInput = view.findViewById<EditText>(R.id.editTextUsername)
        val passwordInput = view.findViewById<EditText>(R.id.editTextPassword)
        val birthDateInput = view.findViewById<EditText>(R.id.editTextBirthdate)
        val weightInput = view.findViewById<EditText>(R.id.editTextWeight)
        val registerButton = view.findViewById<Button>(R.id.buttonRegister)
        val toLoginButton = view.findViewById<TextView>(R.id.goToLogButton)

        birthDateInput.setOnClickListener{
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    birthDateInput.setText(dateFormat.format(selectedDate.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        registerButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            val birthDate = birthDateInput.text.toString()
            val weight = weightInput.text.toString().toFloatOrNull() ?: 0f

            if (username.isNotEmpty() && password.isNotEmpty() && birthDate.isNotEmpty() && weight > 0) {
                val user = User(username = username, password = password, birthdate = birthDate, weight = weight)

                userViewModel.registerUser(user,
                    onSuccess = {
                        Toast.makeText(requireContext(), "Zostałeś zarejestrowany", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    },
                    onFailure = { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(requireContext(), "Wypełnij wszystkie pola", Toast.LENGTH_SHORT).show()
            }
        }

        toLoginButton.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}
package com.example.sporttracker.ui.register

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
import com.example.sporttracker.R
import com.example.sporttracker.models.user.UserViewModel


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
        val toLoginButton = view.findViewById<Button>(R.id.goToLogButton)

        registerButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            val birthDate = birthDateInput.text.toString()
            val weight = weightInput.text.toString().toFloatOrNull() ?: 0f

            if (username.isNotEmpty() && password.isNotEmpty() && birthDate.isNotEmpty() && weight > 0) {
                val user = User(username = username, password = password, birthdate = birthDate, weight = weight)

                userViewModel.registerUser(user,
                    onSuccess = {
                        Toast.makeText(requireContext(), "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    },
                    onFailure = { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show()
            }
        }

        toLoginButton.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}
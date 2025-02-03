package com.example.sporttracker.ui.login

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
import com.example.sporttracker.databinding.FragmentLoginBinding
import com.example.sporttracker.models.user.UserViewModel
import com.example.sporttracker.utils.SharedPreferencesManager


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val usernameInput = view.findViewById<EditText>(R.id.editTextUsername)
        val passwordInput = view.findViewById<EditText>(R.id.editTextPassword)
        val loginButton = view.findViewById<Button>(R.id.buttonLogin)
        val registerText = view.findViewById<TextView>(R.id.goToRegButton)


        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()


            userViewModel.loginUser(username, password,
                onSuccess = { user ->  // Приняли user


                    Toast.makeText(requireContext(), "Вход выполнен!", Toast.LENGTH_SHORT).show()


                    val bundle = Bundle().apply {
                        putString("username", username)  // Можно передавать имя пользователя, если нужно
                        putInt("userId", user.id)
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment, bundle)
                },
                onFailure = { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            )
        }

        registerText.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}

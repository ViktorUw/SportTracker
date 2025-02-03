package com.example.sporttracker.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sporttracker.database.AppDatabase
import com.example.sporttracker.models.user.User
import com.example.sporttracker.repository.UserRepository
import kotlinx.coroutines.launch

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository: UserRepository
    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus: LiveData<Boolean> get() = _registerStatus
    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun register(username: String, password: String, birthdate: String, weight: Float) {
        viewModelScope.launch {
            val user = User(username = username, password = password, birthdate = birthdate, weight = weight)
            userRepository.insertUser(user)
            _registerStatus.value = true
        }
    }
}
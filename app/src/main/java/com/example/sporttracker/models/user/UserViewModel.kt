package com.example.sporttracker.models.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sporttracker.database.AppDatabase
import com.example.sporttracker.repository.UserRepository
import com.example.sporttracker.utils.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository
    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val sharedPrefs = SharedPreferencesManager(application)

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    init {
        userRepository = UserRepository(userDao)
    }

    fun registerUser(user: User, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingUser = userRepository.getUserByUsername(user.username)
            if (existingUser == null) {
                userRepository.insertUser(user)
                sharedPrefs.saveUsername(user.username) // Сохраняем имя в SharedPreferences
                withContext(Dispatchers.Main) { onSuccess() }
            } else {
                withContext(Dispatchers.Main) { onFailure("Użytkownik o takim imieniu juz istnieje!") }
            }
        }
    }

    fun loginUser(username: String, password: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getUser(username, password)
            if (user != null) {
                sharedPrefs.saveUsername(username) // Сохраняем текущего пользователя
                withContext(Dispatchers.Main) { onSuccess(user) }
            } else {
                withContext(Dispatchers.Main) { onFailure("Błedne dane do logowania") }
            }
        }
    }

    fun loadUser() {
        val savedUsername = sharedPrefs.getUsername()
        if (savedUsername.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val userFromDb = userRepository.getUserByUsername(savedUsername)
                _user.postValue(userFromDb)
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.updateUser(user)
        }
    }

    fun setUser(user: User) {
        _user.postValue(user)
    }

}

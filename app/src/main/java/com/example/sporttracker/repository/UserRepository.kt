package com.example.sporttracker.repository

import com.example.sporttracker.database.UserDao
import com.example.sporttracker.models.user.User

class UserRepository(private val userDao: UserDao) {

    fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    fun getUser(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }

    fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}

package com.example.toiletkorea.model.service

import kotlinx.coroutines.flow.Flow
import com.example.toiletkorea.model.User

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun creatAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}
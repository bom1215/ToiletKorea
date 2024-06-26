package com.codeJP.toiletkorea.model.service

import android.content.Intent
import android.content.IntentSender
import com.codeJP.toiletkorea.model.User
import com.codeJP.toiletkorea.ui.login.LoginUiState
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUsername: String

    val currentUser: Flow<User>

    val oneTapClient: SignInClient


    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun createAccount(email: String, password: String)
    suspend fun updateProfile(username: String)
    suspend fun deleteAccount()
    suspend fun signOut()

    suspend fun googleSignIn(): IntentSender?

    suspend fun googleSignInWithIntent(intent: Intent): LoginUiState

    suspend fun googleSignOut()


}
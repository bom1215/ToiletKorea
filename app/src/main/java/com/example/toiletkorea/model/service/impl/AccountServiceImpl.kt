package com.example.toiletkorea.model.service.impl

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.example.toiletkorea.R
import com.example.toiletkorea.TAG
import com.example.toiletkorea.model.User
import com.example.toiletkorea.model.service.AccountService
import com.example.toiletkorea.ui.login.LoginUiState
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val context: Context,
) : AccountService {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()
    override val currentUsername: String
        get() = auth.currentUser?.displayName.orEmpty()
    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous, it.displayName, it.email) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose {auth.removeAuthStateListener(listener)}
        }




    override suspend fun authenticate(email: String, password: String) {
        Log.d(TAG, "로그인 처리중")
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun linkAccount(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser!!.linkWithCredential(credential).await()
    }

    override suspend fun createAccount(email: String, password: String) {
        if (!hasUser){
            auth.createUserWithEmailAndPassword(email, password)
        }
    }

    override suspend fun updateProfile(username: String) {
        val user: FirebaseUser = auth.currentUser!!
        val profileUpdates = userProfileChangeRequest {
                displayName = username
        }
            user.updateProfile(profileUpdates).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile (username) updated.")
                }
            }
    }



    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }
    override suspend fun signOut() {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
        }
        Log.d(TAG, "로그아웃 전 유저 정보 ${auth.currentUser}")
        auth.signOut()
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }

    override val oneTapClient: SignInClient
        get() = Identity.getSignInClient(context)

    // google login

    override suspend fun googleSignIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                googleBuildSignInRequest()
            ).await()
        } catch(e: Exception) {
            Log.d(TAG,"googleSignIn Exception occur",e)
            null
        }
        Log.d(TAG, "googleSignIn result: $result")

        return result?.pendingIntent?.intentSender
    }

    override suspend fun googleSignInWithIntent(intent: Intent): LoginUiState {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            LoginUiState(
                username = user?.uid ?: "User",
            )
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            LoginUiState(
                username = "User",
                errorMessage = e.message
            )
        }
    }

    override suspend fun googleSignOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    private fun googleBuildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

}
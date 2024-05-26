package com.example.toiletkorea.model.service.module

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun auth(): FirebaseAuth = Firebase.auth

//    @Provides fun firestore(): FirebaseFirestore = Firebase.firestore
}
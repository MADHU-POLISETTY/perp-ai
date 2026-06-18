package com.example.perp_ai.data.repository

import com.example.perp_ai.domain.models.Resource
import com.example.perp_ai.domain.models.User
import com.example.perp_ai.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    
    private var cachedUser: User? = null

    override val currentUser: User?
        get() = cachedUser ?: firebaseAuth.currentUser?.let {
            User(uid = it.uid, email = it.email ?: "", name = it.displayName ?: "")
        }

    override suspend fun login(email: String, pass: String): Resource<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, pass).await()
            fetchUserDetails(firebaseAuth.currentUser?.uid ?: "")
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun register(user: User, pass: String): Resource<Unit> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(user.email, pass).await()
            val uid = result.user?.uid ?: return Resource.Error("User ID not found")
            val finalUser = user.copy(uid = uid)
            
            firestore.collection("users").document(uid).set(finalUser).await()
            cachedUser = finalUser
            
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
        cachedUser = null
    }

    override suspend fun resetPassword(email: String): Resource<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun getUserDetails(uid: String): Resource<User> {
        return try {
            val document = firestore.collection("users").document(uid).get().await()
            val user = document.toObject(User::class.java)
            if (user != null) {
                cachedUser = user
                Resource.Success(user)
            } else {
                Resource.Error("User data not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
    
    private suspend fun fetchUserDetails(uid: String) {
        try {
            val document = firestore.collection("users").document(uid).get().await()
            cachedUser = document.toObject(User::class.java)
        } catch (e: Exception) {
            // Silently fail, fallback to Auth profile
        }
    }
}

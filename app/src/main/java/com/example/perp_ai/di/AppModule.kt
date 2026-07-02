package com.example.perp_ai.di

import com.example.perp_ai.data.repository.AuthRepositoryImpl
import com.example.perp_ai.data.repository.InterviewRepositoryImpl
import com.example.perp_ai.data.repository.QuizRepositoryImpl
import com.example.perp_ai.data.repository.ResumeRepositoryImpl
import com.example.perp_ai.domain.repository.AiRepository
import com.example.perp_ai.domain.repository.AuthRepository
import com.example.perp_ai.domain.repository.InterviewRepository
import com.example.perp_ai.domain.repository.QuizRepository
import com.example.perp_ai.domain.repository.ResumeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth, firestore: FirebaseFirestore): AuthRepository {
        return AuthRepositoryImpl(auth, firestore)
    }

    @Provides
    @Singleton
    fun provideInterviewRepository(firestore: FirebaseFirestore): InterviewRepository {
        return InterviewRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideQuizRepository(firestore: FirebaseFirestore): QuizRepository {
        return QuizRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideResumeRepository(
        firestore: FirebaseFirestore,
        aiRepository: AiRepository
    ): ResumeRepository {
        return ResumeRepositoryImpl(firestore, aiRepository)
    }
}

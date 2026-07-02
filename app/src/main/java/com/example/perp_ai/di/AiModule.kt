package com.example.perp_ai.di

import com.example.perp_ai.BuildConfig
import com.example.perp_ai.data.repository.AiRepositoryImpl
import com.example.perp_ai.domain.repository.AiRepository
import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AiModule {

    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        // IMPORTANT: In a real app, store the API key securely (e.g., BuildConfig or secrets.xml)
        // You can get an API key at https://aistudio.google.com/
        return GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }

    @Provides
    @Singleton
    fun provideAiRepository(generativeModel: GenerativeModel): AiRepository {
        return AiRepositoryImpl(generativeModel)
    }
}

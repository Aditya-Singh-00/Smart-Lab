package com.aditya.smartlab.di

import com.aditya.smartlab.data.repository.SmartLabRepository
import com.aditya.smartlab.data.repository.SmartLabRepositoryImpl
import com.aditya.smartlab.util.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance(DATABASE_URL)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideSmartLabRepository(
        db: FirebaseDatabase,
        auth: FirebaseAuth
    ): SmartLabRepository {
        return SmartLabRepositoryImpl(db,auth)
    }
}
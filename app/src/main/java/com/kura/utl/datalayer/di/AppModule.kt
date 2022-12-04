package com.kura.utl.datalayer.di

import android.app.Application
import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kura.utl.datalayer.repository.DatabaseRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Provides
fun provideDatabaseReference(): DatabaseReference = FirebaseDatabase.getInstance().reference

@Provides
fun providesDatabaseRepository(impl: DatabaseRepositoryImp): DatabaseRepositoryImp = impl


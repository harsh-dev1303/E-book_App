package com.example.e_book.DI

import android.content.Context
import com.example.e_book.Data_layer.Repository.repo
import com.example.e_book.Data_layer.UserPreferencesRepository
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DI_Module {
  @Provides
  @Singleton
  fun getfirebasedatabase(): FirebaseDatabase {
      return FirebaseDatabase.getInstance()
  }
    @Provides
    @Singleton
    fun getRepo(firebaseDatabase: FirebaseDatabase ):repo{
        return repo(firebaseDatabase)
    }

}

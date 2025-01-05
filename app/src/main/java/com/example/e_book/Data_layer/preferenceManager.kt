package com.example.e_book.Data_layer

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.e_book.IndividualBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


private val Context.dataStore by preferencesDataStore("User_preferences")
class UserPreferencesRepository(private val context: Context) {
    companion object{
        private val bookMarkedBooks= stringPreferencesKey("bookMarked_Books")     //here we store list of IndividuaBook(we have made a data class by this name just look upon main activity class) data class which will will in json string format "[(),(),etc]"
    }

    val json= Json{ignoreUnknownKeys=true}      //This instance of the Json class handles serialization and deserialization of Book objects to/from JSON strings.

    val give_bookMarkedBookes:Flow<List<IndividualBook>> = context.dataStore.data.map{
        val markedBook=it[bookMarkedBooks]?: "[]"
        json.decodeFromString<List<IndividualBook>>(markedBook)
    }

    suspend fun get_markedBooks(book:IndividualBook){
        context.dataStore.edit {
           val currentBookStoredList=give_bookMarkedBookes.firstOrNull()?: emptyList()
            val updatedBookList=currentBookStoredList + book
            it[bookMarkedBooks]=json.encodeToString(updatedBookList)
        }
    }
    suspend fun deleteBook(book: IndividualBook) {
        context.dataStore.edit {
            val currentBooks = give_bookMarkedBookes.firstOrNull() ?: emptyList()
            val updatedBooks = currentBooks.filter { it != book }
            it[bookMarkedBooks] = json.encodeToString(updatedBooks)
        }
    }



}




package com.example.e_book.Data_layer.Repository

import android.util.Log
import com.example.e_book.BackenedStates.resultStates
import com.example.e_book.Data_layer.Response.BookCategoryModule
import com.example.e_book.Data_layer.Response.BookModule
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class repo @Inject constructor(val firebaseDatabase: FirebaseDatabase) {
    suspend fun GetBookCategory(): Flow<resultStates<List<BookCategoryModule>>> = callbackFlow {
        trySend(resultStates.Loading)

        val ValueListner = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("snapshot",snapshot.toString())
                 var categoryList:List<BookCategoryModule> = emptyList()    //The mapNotNull function in Kotlin is a very handy function that transforms a collection (e.g., a list or sequence) by applying a transformation to each element and automatically removing any null values from the result.
                   categoryList = snapshot.children.map{    //This represents the children(children in bookcategory in database) of a specific DataSnapshot. Each child is a DataSnapshot of its own.
                      it.getValue<BookCategoryModule>()!!            //This uses Firebase's built-in getValue() method to convert the data in the DataSnapshot into an object of the specified type, in this case, BookCategoryModule.
                            //When you use !!, you're effectively saying to the compiler:
                       //
                       //"I am 100% sure this value is not null. If I'm wrong, crash the program."

                  }
                snapshot.children.forEach { child ->
                    Log.d("RawSnapshot", child.value.toString())
                    Log.d("CategoryName", child.child("CategoryName").value.toString())
                    Log.d("ImageUrl", child.child("ImageUrl").value.toString())
                }


                Log.d("categoryList",categoryList.toString())

                trySend(resultStates.success(categoryList))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(resultStates.failure(error.toException()))

            }
        }
        firebaseDatabase.reference.child("BookCategory").addValueEventListener(ValueListner)
        awaitClose{
            firebaseDatabase.reference.child("BookCategory").removeEventListener(ValueListner)
            close()
        }

    }
    suspend fun Books():Flow<resultStates<List<BookModule>>> = callbackFlow {
        trySend(resultStates.Loading)
        val ValueListner=object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var bookList:List<BookModule> = emptyList()
                bookList=snapshot.children.map {
                    it.getValue<BookModule>()!!
                }
                trySend(resultStates.success(bookList))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(resultStates.failure(error.toException()))
            }

        }

        firebaseDatabase.reference.child("Books").addValueEventListener(ValueListner)
        awaitClose{
            firebaseDatabase.reference.child("Books").removeEventListener(ValueListner)
            close()
        }


    }

    suspend fun FilterBook(category:String):Flow<resultStates<List<BookModule>>> = callbackFlow{
        trySend(resultStates.Loading)
        Log.d("category","category:$category")
        val ValueListner=object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("snapshot","snapshot:$snapshot")
                var items:List<BookModule> = emptyList()
                items=snapshot.children.filter {
                    it.getValue<BookModule>()!!.CategoryName==category
                }.map {
                   it.getValue<BookModule>()!!

                }
                Log.d("filter book","List of filter book:${items.toString()}")
                trySend(resultStates.success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(resultStates.failure(error.toException()))
            }

        }
        Log.d("value Listener","$ValueListner")
        firebaseDatabase.reference.child("Books").addValueEventListener(ValueListner)
        awaitClose{
            firebaseDatabase.reference.child("Books").removeEventListener(ValueListner)
            close()
        }
    }
}
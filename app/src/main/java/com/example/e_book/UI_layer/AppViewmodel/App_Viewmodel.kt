package com.example.e_book.UI_layer.AppViewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_book.BackenedStates.resultStates
import com.example.e_book.Data_layer.Repository.repo
import com.example.e_book.FrontenedStates.BookByCategoryStates
import com.example.e_book.FrontenedStates.BookStates
import com.example.e_book.FrontenedStates.FrontResultStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class App_Viewmodel @Inject constructor(val repo: repo): ViewModel() {

    private val _getFrontResultStates = MutableStateFlow(FrontResultStates())
    val getFrontResultStates = _getFrontResultStates.asStateFlow()
    fun GetBook_Category() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.GetBookCategory().collect {
                when (it) {
                    is resultStates.Loading -> {
                        _getFrontResultStates.value = FrontResultStates(Loading = true)
                    }

                    is resultStates.success -> {
                        _getFrontResultStates.value =
                            FrontResultStates(data = it.data, Loading = false)
                    }

                    is resultStates.failure -> {
                        _getFrontResultStates.value =
                            FrontResultStates(error = it.msg, Loading = false)
                    }

                }
            }
        }
    }

    private val _getBookStates = MutableStateFlow(BookStates())
    val getBookStates = _getBookStates.asStateFlow()
    fun GetBook() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.Books().collect {
                when (it) {
                    is resultStates.Loading -> {
                        _getBookStates.value = BookStates(Loading = true)
                    }

                    is resultStates.success -> {
                        _getBookStates.value = BookStates(data = it.data, Loading = false)
                    }

                    is resultStates.failure -> {
                        _getBookStates.value = BookStates(error = it.msg, Loading = false)
                    }
                }

            }
        }
    }
    private val _getBookByCategoryStates= MutableStateFlow(BookByCategoryStates())
    val getBookByCategoryStates=_getBookByCategoryStates.asStateFlow()
    fun GetBookByCategory(category:String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.FilterBook(category).collect {
                when (it) {
                    is resultStates.Loading -> {
                        _getBookByCategoryStates.value = BookByCategoryStates(Loading = true)
                    }
                    is resultStates.success -> {
                        _getBookByCategoryStates.value = BookByCategoryStates(data = it.data, Loading = false)
                        Log.d("book by category list","book list:${it.data.toString()}")
                    }
                    is resultStates.failure -> {
                        _getBookByCategoryStates.value = BookByCategoryStates(error = it.msg, Loading = false)
                    }

                }
            }
}}}
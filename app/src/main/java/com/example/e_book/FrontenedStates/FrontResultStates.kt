package com.example.e_book.FrontenedStates

import com.example.e_book.Data_layer.Response.BookCategoryModule
import kotlinx.coroutines.flow.MutableStateFlow

data class FrontResultStates(
    var Loading:Boolean=false,
    var data:List<BookCategoryModule>?=null,
    var error:Throwable?=null
)

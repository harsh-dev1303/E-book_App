package com.example.e_book.FrontenedStates

import com.example.e_book.Data_layer.Response.BookModule

data class BookByCategoryStates(
    val Loading:Boolean=false,
    val data:List<BookModule>?=null,
    val error:Throwable?=null
)

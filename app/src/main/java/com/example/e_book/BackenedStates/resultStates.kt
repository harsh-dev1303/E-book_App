package com.example.e_book.BackenedStates

sealed class resultStates <out T>{          //sealed class allows you to define a fixed set of subclasses that can extend it.
    object Loading:resultStates<Nothing>()
    data class success<out T>(val data:T):resultStates<T>()
    data class failure(val msg:Throwable):resultStates<Nothing>()
}
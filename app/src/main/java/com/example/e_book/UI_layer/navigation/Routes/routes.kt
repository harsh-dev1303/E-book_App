package com.example.e_book.UI_layer.navigation.Routes

import kotlinx.serialization.Serializable


@Serializable
sealed class routes {
    @Serializable
    object Book

    @Serializable
    object Category

    @Serializable
    object Home

    @Serializable
    data class BookByCategory(val data:String)

    @Serializable
    data class PdfView(
        val dataURL: String,
        val dataBookName: String,
        val BookImage: String,
        val BookAuthor: String
    )

    @Serializable
    object bookMark

}
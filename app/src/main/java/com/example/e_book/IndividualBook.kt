package com.example.e_book

import kotlinx.serialization.Serializable


@Serializable
data class IndividualBook(
    val Book_Image:String,
    val Book_Name:String,
    val Book_Author:String,
    val Page_No:Int,
    val pdf_Url:String
)

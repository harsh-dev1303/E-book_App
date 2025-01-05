package com.example.e_book.UI_layer.navigation.Navigation_Screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.e_book.Data_layer.UserPreferencesRepository
import com.example.e_book.UI_layer.Screens.BooKMark_Screen
import com.example.e_book.UI_layer.Screens.BookByCategory_Screen
import com.example.e_book.UI_layer.Screens.Book_Screen
import com.example.e_book.UI_layer.Screens.Category_Screen
import com.example.e_book.UI_layer.Screens.Home_Screen
import com.example.e_book.UI_layer.Screens.pdfView_Screen
import com.example.e_book.UI_layer.navigation.Routes.routes

@Composable
fun App_navigation(userPreference: UserPreferencesRepository){
    val navController = rememberNavController()
    NavHost(navController=navController, startDestination = routes.Home)
    {
        composable<routes.Home>{
            Home_Screen(navController)
        }
        composable<routes.Category>{
            Category_Screen(navController)
        }
        composable<routes.Book>{
            Book_Screen(navController)
        }
        composable<routes.BookByCategory> {
            val category=it.toRoute<routes.BookByCategory>().data
            BookByCategory_Screen(category = category.toString(),navController)

        }
        composable<routes.PdfView> {
            val Pdfurl=it.toRoute<routes.PdfView>().dataURL
            val BookName=it.toRoute<routes.PdfView>().dataBookName
            val BookImage=it.toRoute<routes.PdfView>().BookImage
            val BookAuthor=it.toRoute<routes.PdfView>().BookAuthor
            pdfView_Screen(pdfurl = Pdfurl.toString(),BookName,navController,BookImage,BookAuthor,userPreference)

        }
        composable<routes.bookMark> {
          /* val data_pageno=it.toRoute<routes.bookMark>().pageno
            val data_bookname=it.toRoute<routes.bookMark>().book_name
            val data_pdfurl=it.toRoute<routes.bookMark>().pdfurl
            val data_bookimage=it.toRoute<routes.bookMark>().BookImage
            val data_bookauthor=it.toRoute<routes.bookMark>().BookAuthor*/
            BooKMark_Screen(navController,userPreference)

        }


    }
}
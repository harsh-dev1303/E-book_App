package com.example.e_book.UI_layer.Screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.e_book.Data_layer.UserPreferencesRepository
import com.example.e_book.UI_layer.navigation.Routes.routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BooKMark_Screen(
    navController: NavHostController,
    userPreference: UserPreferencesRepository,
    /*data_pageno: Int,
    data_bookname: String,
    data_pdfurl: String,
    data_bookimage: String,
    data_bookauthor: String*/
) {
    val BooksMarked by userPreference.give_bookMarkedBookes.collectAsStateWithLifecycle(null)
    val coroutineScope = rememberCoroutineScope()
    val books=BooksMarked?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "BookMark")
                },
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                windowInsets = WindowInsets(top = 0)
            )
        }

    ) {innerpadding->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(innerpadding)) {
            //Log.d("Book mark called","bookimageurl:$BooksMarked,bookname:$Book_Name,bookauthor:$Book_Author,pageno:$Page_No,pdfurl:$pdf_Url")
            /*item {
                Card(
                    modifier = Modifier.padding(top = 80.dp).fillMaxWidth()
                        .wrapContentHeight().padding(horizontal = 10.dp)
                        .clickable {
                            navController.navigate(route = routes.PdfView(dataURL = pdf_Url.toString(), dataBookName = Book_Name.toString(),BookImage=Book_Image.toString(),BookAuthor=Book_Author.toString()))
                        }
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = Book_Image,
                            contentDescription = null,
                            modifier = Modifier.height(100.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        /* AsyncImage(
                          model =BookImage,
                          contentDescription = null,
                       )*/
                        //Log.d("Book Image","bookimage url$BookImage")
                        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Book Name:$Book_Name")
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Book Author:$Book_Author")
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Book pageNo:$Page_No")
                        }
                        Row(modifier = Modifier.padding(start = 10.dp)) {
                           IconButton(
                               onClick = {
                                   coroutineScope.launch {
                                         userPreference.clear_bookMarkedBooks()

                                   }
                               }
                           ) {
                              Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                           }
                        }
                    }
                }

            }*/
            items(books){
                Card(modifier = Modifier.padding(top = 20.dp).fillMaxWidth()
                    .wrapContentHeight().padding(horizontal = 10.dp)
                    .clickable {
                        navController.navigate(routes.PdfView(dataURL = it.pdf_Url, dataBookName = it.Book_Name,it.Book_Image,it.Book_Author))
                    }
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model =it.Book_Image,
                            contentDescription = null,
                            modifier = Modifier.height(100.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        /* AsyncImage(
                            model =BookImage,
                            contentDescription = null,
                         )*/
                        //Log.d("Book Image","bookimage url$BookImage")
                        Column (modifier = Modifier.padding(horizontal = 10.dp)){
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Book Name:${it.Book_Name}")
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Book Author:${it.Book_Author}")
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = "Book pageNO:${it.Page_No}")
                        }
                        Row(modifier = Modifier.padding(start = 10.dp)) {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        userPreference.deleteBook(it)

                                    }
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                            }
                        }

                    }

                }
                Spacer(modifier = Modifier.height(10.dp))
                  //eachBook(BookName=it.Book_Name,BookAuthor=it.Book_Author,BookImage=it.Book_Image,BookPdf=it.pdf_Url,BookPageno=it.Page_No,NavController=navController,userPreference,coroutineScope)
            }
        }
    }
}

/*@Composable
fun eachBook(
    BookName: String,
    BookAuthor: String,
    BookImage: String,
    BookPdf: String,
    BookPageno: Int,
    NavController: NavHostController,
    userPreference: UserPreferencesRepository,
    coroutineScope: CoroutineScope,

    ){
    Card(modifier = Modifier.padding(top = 20.dp).fillMaxWidth()
        .wrapContentHeight().padding(horizontal = 10.dp)
        .clickable {
            NavController.navigate(routes.PdfView(dataURL = BookPdf, dataBookName = BookName,BookImage,BookAuthor))
        }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model =BookImage,
                contentDescription = null,
                modifier = Modifier.height(100.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            /* AsyncImage(
                model =BookImage,
                contentDescription = null,
             )*/
            Log.d("Book Image","bookimage url$BookImage")
            Column (modifier = Modifier.padding(horizontal = 10.dp)){
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Book Name:$BookName")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Book Author:$BookAuthor")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Book pageNO:$BookPageno")
            }
            Row(modifier = Modifier.padding(start = 10.dp)) {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            userPreference.deleteBook()

                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                }
            }

        }

    }
    Spacer(modifier = Modifier.height(10.dp))

}*/
package com.example.e_book.UI_layer.Screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.e_book.UI_layer.AppViewmodel.App_Viewmodel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookByCategory_Screen(category:String,NavController:NavHostController,viewmodel:App_Viewmodel= hiltViewModel(),modifier: Modifier=Modifier.padding(10.dp)){
    val states=viewmodel.getBookByCategoryStates.collectAsState()
    val data=states.value.data?: emptyList()
    val context= LocalContext.current
    LaunchedEffect(Unit) {
        viewmodel.GetBookByCategory(category)
    }
    when{
        states.value.Loading->{
            Box(modifier= Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        states.value.data!=null->{
            Scaffold (
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text =category)
                        },
                        navigationIcon = {
                            IconButton(onClick = {NavController.popBackStack()}) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                            }
                        },
                        windowInsets = WindowInsets(top = 0.dp)
                    )
                }
            ){innerpadding->
                LazyColumn(modifier = Modifier.fillMaxSize().padding(innerpadding).background(Color.Black)) {
                    Log.d("Lazy column", "lazy column is running")
                    items(data) {
                        Log.d("each book", "is it running")
                        EachBook(
                            BookName = it.BookName,
                            BookAuthor = it.BookAuthor,
                            BookImage = it.BookImage,
                            BookPdf = it.BookPdf,
                            BookCategory = it.CategoryName,
                            NavController = NavController
                        )
                    }
                }
            }
        }
        states.value.error!=null->{
            Box(modifier = Modifier.fillMaxSize().background(Color.White),contentAlignment = Alignment.Center){
                Column {
                 Text(text="Oops! something went wrong")
                    Text(text="Check your internet connection")
                }
                Toast.makeText(context,"error:${states.value.error.toString()}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
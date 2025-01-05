package com.example.e_book.UI_layer.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import coil.compose.AsyncImage

import com.example.e_book.UI_layer.AppViewmodel.App_Viewmodel
import com.example.e_book.UI_layer.navigation.Routes.routes


@Composable
fun Book_Screen(Navcontroller: NavHostController, viewmodel: App_Viewmodel = hiltViewModel()){
   val context= LocalContext.current
   val MyBookStates=viewmodel.getBookStates.collectAsState()
   val data=MyBookStates.value.data?: emptyList()
   LaunchedEffect(Unit) {
      viewmodel.GetBook()
      Log.d("book call","called")
   }
   when{
      MyBookStates.value.Loading->{
         Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
         }
      }
      MyBookStates.value.data!=null->{
            LazyColumn(modifier = Modifier.fillMaxSize()) {
               items(data){
                  EachBook(BookName = it.BookName, BookAuthor = it.BookAuthor, BookImage = it.BookImage, BookPdf = it.BookPdf, BookCategory = it.CategoryName,NavController=Navcontroller)
               }
            }

      }
      MyBookStates.value.error!=null->{
         Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center){
            Text(text = "Oops! something went wrong")
         }
         Toast.makeText(context,"error:${MyBookStates.value.error.toString()}", Toast.LENGTH_SHORT).show()

      }
   }
}

@Composable
fun EachBook(
   BookName: String,
   BookAuthor: String,
   BookImage: String,
   BookPdf: String,
   BookCategory: String,
   NavController: NavHostController
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
            Text(text = "Book category:$BookCategory")
         }
         
      }

   }
   Spacer(modifier = Modifier.height(10.dp))

}
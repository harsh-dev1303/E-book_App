package com.example.e_book.UI_layer.Screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

import com.example.e_book.R
import com.example.e_book.UI_layer.AppViewmodel.App_Viewmodel
import com.example.e_book.UI_layer.navigation.Routes.routes
import java.util.Locale.Category

@SuppressLint("Range")
@Composable
fun Category_Screen(navcontroller:NavHostController,viewmodel: App_Viewmodel= hiltViewModel()){
      val context= LocalContext.current
      val CategoryStates=viewmodel.getFrontResultStates.collectAsState()
      val data=CategoryStates.value.data?: emptyList()
      LaunchedEffect(Unit) {
          viewmodel.GetBook_Category()
      }
      when{
          CategoryStates.value.Loading->{
              Box(modifier=Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center){
                  CircularProgressIndicator()
              }
          }
          CategoryStates.value.data!=null-> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
              items(data){
                 Card(modifier = Modifier.padding(top = 20.dp).fillMaxWidth()
                     .wrapContentHeight().padding(horizontal = 10.dp)
                     .clickable {
                          navcontroller.navigate(route = routes.BookByCategory(data = it.CategoryName))
                     }
                 ) {
                     Column(modifier = Modifier.fillMaxWidth()) {
                         AsyncImage(
                             model = it.ImageUrl,
                             contentDescription = null,
                             modifier = Modifier.height(100.dp),
                             alignment = Alignment.Center,

                         )


                        /* AsyncImage(
                             model = it.ImageUrl,
                             contentDescription = null,
                             modifier = Modifier.height(200.dp)
                         )*/
                         Log.d("image",it.ImageUrl)
                         Log.d("image",it.CategoryName)
                         Log.d("BookCategoryModule",it.toString())
                         Spacer(modifier=Modifier.height(10.dp))
                         Text(text = "Book Category:${it.CategoryName}")
                     }

                 }
                  Spacer(modifier = Modifier.height(10.dp))
              }

            }
          }
          CategoryStates.value.error!=null->{
              Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center){
                Text(text = "Oops! something went wrong")
          }
              Toast.makeText(context,"error:${CategoryStates.value.error.toString()}",Toast.LENGTH_SHORT).show()
              Log.d("in error",CategoryStates.value.error.toString())
          }

  }
}
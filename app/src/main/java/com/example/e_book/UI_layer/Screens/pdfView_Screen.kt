package com.example.e_book.UI_layer.Screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.e_book.Data_layer.UserPreferencesRepository
import com.example.e_book.IndividualBook
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun pdfView_Screen(
    pdfurl: String,
    BookName: String,
    navController: NavHostController,
    BookImage: String,
    BookAuthor: String,
    userPreference: UserPreferencesRepository
) {
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote(pdfurl),
        isZoomEnable = true
    )
    val book=IndividualBook(Book_Name = BookName, Book_Image = BookImage, Book_Author = BookAuthor, Page_No = pdfState.currentPage, pdf_Url = pdfurl)
    val CoroutineScope= rememberCoroutineScope()
    val contex= LocalContext.current

   // var isLoaded= remember { derivedStateOf{pdfState.isLoaded} }
    //var percent= remember { derivedStateOf{pdfState.loadPercent/100f} }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = BookName)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                windowInsets = WindowInsets(top = 0),
                actions = {
                    IconButton(onClick = {
                        CoroutineScope.launch {
                            userPreference.get_markedBooks(book)
                        }
                        Toast.makeText(contex,"Saved in bookMark",Toast.LENGTH_SHORT).show()
                    }
                       // navController.navigate(routes.bookMark(pageno = pdfState.currentPage,book_name = BookName, pdfurl =  pdfurl,BookImage,BookAuthor))
                    ) {
                        Icon(imageVector = Icons.Default.Bookmarks, contentDescription = null)
                    }
                }
            )

        }
    ) { innerpadding ->
        /*when{
            !pdfState.isLoaded->{
                Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center){
                    Log.d("progress indicator page","progress indicator page called")
                    CircularProgressIndicator(
                        progress = {
                            pdfState.loadPercent / 100f // Convert 0-100 to 0.0-1.0
                        },

                    )
                }
            }
            pdfState.isLoaded->{*/
        Box(modifier = Modifier.padding(innerpadding)) {
            /*LaunchedEffect(pdfState.isLoaded) {
               Log.d("PDF State Change", "isLoaded: ${pdfState.isLoaded}")
            }*/
           /* when {
                !isLoaded.value -> {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Log.d("progress indicator page", "progress indicator page called")
                        CircularProgressIndicator(
                            progress = {
                                percent.value // Convert 0-100 to 0.0-1.0
                            }

                            )
                        Log.d("pdfstate", "isLoaded:${pdfState.isLoaded.toString()},loadingpercent:${pdfState}")
                    }
                }

                isLoaded.value -> {*/
            Log.d("pdfstate", "isLoaded:${pdfState.isLoaded.toString()},loadingpercent:${pdfState}")
                    Log.d("pdf", "pdf called")
                    VerticalPDFReader(
                        state = pdfState,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Gray)
                    )
            if(!pdfState.isLoaded){
                Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center){
                    Log.d("progress indicator","pdfState.isLoaded:${pdfState.isLoaded}")
                    CircularProgressIndicator(
                        progress = {
                           pdfState.loadPercent/100f
                        },
                        modifier = Modifier.size(50.dp), // Sets the size of the indicator
                        color = Color.Blue, // Changes the indicator's color
                    )
                   
                }
            }
                }

            }
        }






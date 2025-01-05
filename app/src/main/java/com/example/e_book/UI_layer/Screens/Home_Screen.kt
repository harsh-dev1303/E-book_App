package com.example.e_book.UI_layer.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.e_book.UI_layer.navigation.Routes.routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home_Screen(navcontroller: NavHostController){
    var scope=rememberCoroutineScope()
    val tabs= listOf(
        tab("Category", Icons.Default.Category),
        tab("Books",Icons.Default.Book)
    )
    var pagerState= rememberPagerState(pageCount = {tabs.size})
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "BookNest")
                },
                navigationIcon = {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = null, modifier = Modifier.padding(start =10.dp))
                },
                windowInsets = WindowInsets(top=0),
                actions = {
                    IconButton(onClick = {navcontroller.navigate(route = routes.bookMark)}) {
                           Icon(imageVector = Icons.Default.Bookmarks, contentDescription = null)
                    }
                }
            )
        }
    ) { innerpadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerpadding)) {
            TabRow(selectedTabIndex = pagerState.currentPage, modifier = Modifier.fillMaxWidth()) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = tab.name) },
                        icon = { Icon(tab.Icon, contentDescription = null) }
                    )
                }

            }
            HorizontalPager(state = pagerState) {
                when (it) {
                    0 -> Category_Screen(navcontroller)
                    1 -> Book_Screen(navcontroller)
                }
            }
        }

    }
}
data class tab(
    val name:String,
    val Icon:ImageVector
)

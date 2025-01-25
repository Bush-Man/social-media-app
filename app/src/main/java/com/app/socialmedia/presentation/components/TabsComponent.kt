package com.app.socialmedia.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.socialmedia.presentation.feed.FeedScreen
import com.app.socialmedia.presentation.profile.ProfilePosts
import kotlinx.coroutines.launch

@Composable
fun TabsComponent(
    tabs:List<String>?,
    pagerState:PagerState,
    modifier: Modifier,
    scrollableBody:Boolean = false,
    navController: NavController
){

    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = modifier.fillMaxWidth(),

        containerColor = Color.White

        ) {
        if (tabs != null) {
            tabs.forEachIndexed { index, title ->
                Tab(

                    selected = pagerState.currentPage == index,
                    text = { Text(text = title)},

                    onClick = { scope.launch{pagerState.animateScrollToPage(index) }}
                )
            }
        }
    }

        Column(
            modifier=if(scrollableBody){modifier.verticalScroll(rememberScrollState())} else modifier
        ){
            HorizontalPager(state = pagerState, modifier = modifier.fillMaxSize()) {page  ->


                    when(page){
                        0 -> ProfilePosts(navController = navController)
                        1 -> Column{repeat(6){ Text("hello screen 1")}}
                        2 ->Column{ repeat(6){ Text("hello screen 2")}}






                }

            }

    }
    }






package com.app.socialmedia.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.socialmedia.presentation.components.TabsComponent
import com.app.socialmedia.presentation.feed.FeedScreen
import com.app.socialmedia.presentation.navigation.Screen
import com.app.socialmedia.presentation.profile.ProfilePosts
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController
){
    val homeScreenTabs = listOf("feeds","following","saved","groups")
    val homeScreenPagerState = rememberPagerState(initialPage = 0, pageCount ={ homeScreenTabs.size})
    HomeTabsComponent(
        tabs = homeScreenTabs,
        pagerState = homeScreenPagerState,
        modifier = Modifier,
        scrollableBody = false,
        navController = navController
    )
}

@Composable
fun HomeTabsComponent(
    tabs:List<String>?,
    pagerState: PagerState,
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
                    text = { Text(text = title) },

                    onClick = { scope.launch{pagerState.animateScrollToPage(index) }}
                )
            }
        }
    }

    Column(
        modifier=if(scrollableBody){modifier.verticalScroll(rememberScrollState())} else modifier
    ){
        Spacer(modifier = modifier.height(47.dp))
        HorizontalPager(state = pagerState, modifier = modifier.fillMaxSize()) {page  ->


            when(page){
                0 -> FeedScreen(navController = navController)
                1 -> Column{repeat(6){ Text("hello screen 1") }}
                2 -> Column{ repeat(6){ Text("hello screen 2") }}
                3 -> Column{ repeat(6){ Text("hello screen 2") }}






            }

        }

    }
}
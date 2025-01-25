package com.app.socialmedia.presentation.components

import android.annotation.SuppressLint
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FeedPosts(
    modifier: Modifier,
    navController: NavController
){
           //This is where we will be loadinng data from the api
    Scaffold {


        for (i in 1..10) {
          Text(text = "post$i")
        }
    }

    }


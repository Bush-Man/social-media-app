package com.app.socialmedia.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.socialmedia.presentation.auth.LoginScreen
import com.app.socialmedia.presentation.auth.LogoutScreen
import com.app.socialmedia.presentation.auth.RegisterScreen
import com.app.socialmedia.presentation.parent_screen_layout.ParentScreenLayout
import com.app.socialmedia.presentation.post.PostScreen
import com.app.socialmedia.util.AuthTokenManager


sealed class Screen(val route:String){
    data object LoginScreen:Screen("login_screen")
    data object RegisterScreen:Screen("register_screen")
    data object HomeScreen:Screen("home_screen")
    data object PostScreen:Screen("post_screen/{postId}"){
        fun createRoute(postId:String) = "post_screen/$postId"
    }
    data object LogoutScreen:Screen("log_out")


}

@Composable
fun NavGraphSetup(navController: NavHostController) {
    val context = LocalContext.current
    val authTokenManager = AuthTokenManager(context)

    val token = authTokenManager.getAuthToken()
    val isAuthenticate = !token.collectAsState(initial = null).value.isNullOrEmpty()
    NavHost(
        navController = navController,
        startDestination = if(isAuthenticate) {Screen.HomeScreen.route }else Screen.LoginScreen.route
    ) {
        composable(Screen.LoginScreen.route) { LoginScreen(navController=navController) }
        composable(Screen.RegisterScreen.route) { RegisterScreen(navController=navController) }

        composable(Screen.HomeScreen.route) {
            ParentScreenLayout(modifier = Modifier, navController = navController, authTokenManager = authTokenManager)
        }
        composable(Screen.PostScreen.route, arguments = listOf(navArgument("postId"){type = NavType.StringType})
        ){backStackEntry ->
        val postId = backStackEntry.arguments?.getString("postId")
            if (postId != null) {
                PostScreen(postId = postId, navController = navController)
            }
        }

        composable(Screen.LogoutScreen.route) {
            LogoutScreen(navController = navController)
        }
    }
}





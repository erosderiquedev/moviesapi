package com.erosutuidev.moviesapi.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.erosutuidev.moviesapi.movieList.presentation.MovieListViewModel
import com.erosutuidev.moviesapi.movieList.util.Screen
import com.erosutuidev.moviesapi.ui.theme.MoviesapiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesapiTheme {
                val movieListViewModel = hiltViewModel<MovieListViewModel>()
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) {
                        HomeScreen(navController = navController)
                    }
                    composable(
                        Screen.Details.route + "/{movieId}",
                        arguments = listOf(navArgument("movieId") {type = NavType.IntType})
                    ) {navBackStackEntry ->
//                        DetailsScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesapiTheme {
        Greeting("Android")
    }
}
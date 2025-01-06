package com.erosutuidev.moviesapi.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erosutuidev.moviesapi.R
import com.erosutuidev.moviesapi.movieList.presentation.MovieListUiEvent
import com.erosutuidev.moviesapi.movieList.presentation.MovieListViewModel
import com.erosutuidev.moviesapi.movieList.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val movieState = movieListViewModel.movieListState.collectAsState().value

    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomNavController = bottomNavController,
                onEvent = movieListViewModel::onEvent
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (movieState.isCurrentPopularScreen) stringResource(id = R.string.popular)
                        else stringResource(id = R.string.upcoming)
                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    ) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            NavHost(navController = bottomNavController, startDestination = Screen.PopularMovieList.route) {
                composable(Screen.PopularMovieList.route) {
                    PopularMoviesScreen(
                        navController = navController,
                        movieListState = movieState,
                        onEnvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.UpcomingMovieList.route) {
                    UpComingMoviesScreen(
                        navController = navController,
                        movieListState = movieState,
                        onEnvent = movieListViewModel::onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController,
    onEvent: (MovieListUiEvent) -> Unit
) {
    val items = listOf(
        BottomItem(title = stringResource(R.string.popular), icon = Icons.Rounded.Movie),
        BottomItem(title = stringResource(R.string.upcoming), icon = Icons.Rounded.Upcoming)
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index
                        when (selected.intValue) {
                            0 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.PopularMovieList.route)
                            }

                            1 -> {
                                onEvent(MovieListUiEvent.Navigate)
                                bottomNavController.popBackStack()
                                bottomNavController.navigate(Screen.UpcomingMovieList.route)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(text = bottomItem.title)
                    }
                )
            }
        }
    }

}

data class BottomItem(
    val title: String,
    val icon: ImageVector
)
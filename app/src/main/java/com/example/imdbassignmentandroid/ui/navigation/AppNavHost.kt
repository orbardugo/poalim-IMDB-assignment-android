// ui/navigation/AppNavHost.kt
package com.example.imdbassignmentandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.imdbassignmentandroid.ui.home.HomeScreen
import com.example.imdbassignmentandroid.ui.details.DetailsScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate("${Screen.Details.route}/${movie.id}")
                },
                onTvShowClick = { tvShow ->
                    navController.navigate("${Screen.Details.route}/${tvShow.id}")
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                },
                onFavoritesClick = {
                    navController.navigate(Screen.Favorites.route)
                }
            )
        }

        composable(route = "${Screen.Details.route}/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments
                ?.getString("itemId")
                ?.toIntOrNull()

            DetailsScreen(itemId = itemId)
        }

        composable(Screen.Search.route) {
            DetailsScreen(itemId = null)
        }

        composable(Screen.Favorites.route) {
            DetailsScreen(itemId = null)
        }
    }
}

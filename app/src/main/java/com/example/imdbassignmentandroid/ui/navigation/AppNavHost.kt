// ui/navigation/AppNavHost.kt
package com.example.imdbassignmentandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.imdbassignmentandroid.domain.model.MediaType
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
                    navController.navigate("${Screen.Details.route}/${MediaType.MOVIE.name}/${movie.id}")
                },
                onTvShowClick = { tvShow ->
                    navController.navigate("${Screen.Details.route}/${MediaType.TV.name}/${tvShow.id}")
                },
                onNavigateToDetails = { mediaType, id ->
                    navController.navigate("${Screen.Details.route}/${mediaType.name}/$id")
                }
            )
        }

        composable(
            route = "${Screen.Details.route}/{mediaType}/{itemId}",
            arguments = listOf(
                navArgument("mediaType") { type = NavType.StringType },
                navArgument("itemId") { type = NavType.IntType }
            )
        ) {
            DetailsScreen()
        }
    }
}

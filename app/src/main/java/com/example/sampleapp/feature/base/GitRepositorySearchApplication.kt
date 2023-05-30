package com.example.sampleapp.feature.base

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sampleapp.R
import com.example.sampleapp.feature.gitRepositorySearch.GitRepositoryDetailsScreen
import com.example.sampleapp.feature.gitRepositorySearch.GitRepositorySearchListScreen
import com.example.sampleapp.feature.gitRepositorySearch.StarredGitRepositoriesScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun GitRepositorySearchApplication(
) {
    val navController = rememberNavController()
    GitRepositorySearchNavHost(
        navController = navController
    )
}

@Composable
fun GitRepositorySearchNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "search") {
        composable(Screen.Search.route) {
            GitRepositorySearchListScreen(
                navController = navController,
                onRepositoryClick = {
                    val encodedUrl = URLEncoder.encode(it.url, StandardCharsets.UTF_8.toString())
                    navController.navigate("gitRepositoryDetail/${encodedUrl}/${it.isStarred}")
                }
            )
        }
        composable(
            "gitRepositoryDetail/{repositoryUrl}/{isStarred}",
            arguments = listOf(navArgument("repositoryUrl") {
                type = NavType.StringType
            }, navArgument("isStarred") {
                type = NavType.BoolType
            })
        ) {
            GitRepositoryDetailsScreen()
        }
        composable(
            Screen.Saved.route,
            arguments = listOf(navArgument("repositoryUrl") {
                type = NavType.StringType
            })
        ) {
            StarredGitRepositoriesScreen(navController = navController) {
                val encodedUrl = URLEncoder.encode(it.url, StandardCharsets.UTF_8.toString())
                navController.navigate("gitRepositoryDetail/${encodedUrl}/${it.isStarred}")
            }
        }
    }
}

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Search : Screen("search", R.string.screen_tab_search)
    object Saved : Screen("saved/{repositoryUrl}", R.string.screen_tab_saved)
}
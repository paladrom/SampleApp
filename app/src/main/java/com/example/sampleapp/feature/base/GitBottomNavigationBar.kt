package com.example.sampleapp.feature.base

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sampleapp.ui.ComposeTheme

@Composable
fun GitBottomNavigationBar(navController: NavController) {
    BottomNavigation(modifier = Modifier.height(60.dp)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        listOf(
            Screen.Search,
            Screen.Saved,
        ).forEach { screen ->
            BottomNavigationItem(
                icon = {
                    when (screen) {
                        is Screen.Search -> {
                            Icon(Icons.Filled.Search, contentDescription = null)
                        }
                        Screen.Saved -> {
                            Icon(Icons.Filled.Star, contentDescription = null)
                        }
                    }
                },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GitBottomNavigationBarPreview() {
    ComposeTheme {
        Surface {
            GitBottomNavigationBar(
                rememberNavController()
            )
        }
    }
}
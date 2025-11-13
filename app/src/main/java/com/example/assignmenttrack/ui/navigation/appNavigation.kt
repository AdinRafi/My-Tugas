package com.example.assignmenttrack.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignmenttrack.ui.screens.MainDashboard
import com.example.assignmenttrack.ui.screens.AddTaskScreen



// handle the type safety navigation between different screens.
sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object AddTask : Screen("add_task")
}


// host the navigation graph and define the different screens.
@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        enterTransition = { slideInVertically( animationSpec = tween(500), initialOffsetY = { it } ) + fadeIn() },
        startDestination = Screen.Dashboard.route
    ) {
        composable( Screen.Dashboard.route){
            MainDashboard(
                onAddTaskClick = { navController.navigate(Screen.AddTask.route) }
            )
        }

        composable(Screen.AddTask.route){
            AddTaskScreen(
                /*TODO: create a pop back*/
            )
        }
    }
}
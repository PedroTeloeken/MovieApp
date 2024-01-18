package br.com.movieapp.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import br.com.movieapp.core.presentation.navigation.BottomNavigationBar
import br.com.movieapp.core.presentation.navigation.DetailsScreenNav
import br.com.movieapp.core.presentation.navigation.NavigationGraph
import br.com.movieapp.core.presentation.navigation.currentRoute

@Composable
fun MainScreen(
    navController: NavHostController
) {

    Scaffold(
        bottomBar = {
            if (currentRoute(navController = navController) != DetailsScreenNav.DetailsScreen.route) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
            NavigationGraph(navController = navController)
        }

    }

}

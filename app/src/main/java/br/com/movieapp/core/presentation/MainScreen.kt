package br.com.movieapp.core.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import br.com.movieapp.core.presentation.navigation.BottomNavigationBar
import br.com.movieapp.core.presentation.navigation.NavigationGraph

@Composable
fun MainScreen(
    navController: NavHostController
) {

    Scaffold(
      bottomBar = {
          BottomNavigationBar(navController = navController)
      },
        content = { innerPadding ->
            NavigationGraph(navController = navController)
        }
    )

}

package br.com.movieapp.core.presentation.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.yellow

@Composable
fun BottomNavigationBar(
    navController: NavController
) {

    val items = listOf(
        BottomNavItem.MoviePopular,
        BottomNavItem.MovieSearch,
        BottomNavItem.MovieFavorite
    )

    BottomNavigation(
        contentColor = yellow,
        backgroundColor = black
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { destinations ->
            BottomNavigationItem(
                selected = currentRoute == destinations.route,
                onClick = {
                          navController.navigate(destinations.route){
                              //permite que a nova tela esteja no top da pilha
                              launchSingleTop = true
                          }
                },
                icon = {
                    Icon(imageVector = destinations.icon, contentDescription = null)
                },
                label = {
                    Text(text = destinations.title)
                }
            )
        }
    }

}
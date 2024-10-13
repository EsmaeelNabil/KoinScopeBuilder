package dev.supersam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.supersam.screens.DetailScreen
import dev.supersam.screens.HomeScreen
import dev.supersam.screens.ListScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen() {
    @Serializable
    data object Home : Screen()

    @Serializable
    data object List : Screen()

    @Serializable
    data object Detail : Screen()
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val navController = rememberNavController()
                NavHost(navController, Screen.Home) {
                    composable<Screen.Home> {
                        HomeScreen(
                            openDetails = { navController.navigate(Screen.Detail) },
                            openList = { navController.navigate(Screen.List) }
                        )
                    }
                    composable<Screen.Detail> {
                        DetailScreen(1, openList = { navController.navigate(Screen.List) })
                    }
                    composable<Screen.List> {
                        ListScreen(1, openDetails = { navController.navigate(Screen.Detail) })
                    }
                }
            }
        }
    }

}



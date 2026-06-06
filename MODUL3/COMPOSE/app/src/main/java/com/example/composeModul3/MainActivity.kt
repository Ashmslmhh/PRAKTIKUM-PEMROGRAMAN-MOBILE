package com.example.composeModul3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeModul3.data.animeList
import com.example.composeModul3.ui.components.AnimeList
import com.example.composeModul3.ui.theme.COMPOSETheme
import androidx.compose.foundation.lazy.items
import com.example.composeModul3.ui.components.AnimeCarousel
import com.example.composeModul3.ui.screen.DetailsScreen
import com.example.composeModul3.ui.screen.SettingScreen
import androidx.compose.material.icons.filled.Language
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            COMPOSETheme(darkTheme = true) {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val viewModel: AnimeViewModel = viewModel()
    NavHost(navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("details/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            DetailsScreen(title = title, navController)
        }
        composable("setting") { SettingScreen(navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name), fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { navController.navigate("setting") }) {
                        Icon(Icons.Default.Language, contentDescription = "settings")
                    }
                }
            )
        }
    ) { innerPadding ->
        Main(paddingValues = innerPadding, navController = navController)
    }
}

@Composable
fun Main(paddingValues: PaddingValues, navController: NavHostController) {
    LazyColumn(contentPadding = paddingValues) {
        item {
            AnimeCarousel(animeList = animeList)
        }
        items(animeList) { anime ->
            AnimeList(anime = anime, navController = navController)
        }
    }
}
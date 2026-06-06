package com.example.composeModul5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeModul5.data.animeList
import com.example.composeModul5.ui.components.AnimeCarousel
import com.example.composeModul5.ui.components.AnimeList
import com.example.composeModul5.ui.screen.DetailsScreen
import com.example.composeModul5.ui.screen.MovieDetailScreen
import com.example.composeModul5.ui.screen.MovieScreen
import com.example.composeModul5.ui.screen.SettingScreen
import com.example.composeModul5.ui.theme.COMPOSETheme
import com.example.composeModul5.viewmodel.AnimeViewModel
import com.example.composeModul5.viewmodel.AnimeViewModelFactory
import com.example.composeModul5.viewmodel.MovieUiState
import com.example.composeModul5.viewmodel.MovieViewModel
import com.example.composeModul5.viewmodel.MovieViewModelFactory

class MainActivity : ComponentActivity() {
    private val animeViewModel: AnimeViewModel by viewModels {
        AnimeViewModelFactory("MuhammadFulan")
    }

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            COMPOSETheme(darkTheme = true) {
                MyApp(animeViewModel = animeViewModel, movieViewModel = movieViewModel)
            }
        }
    }
}

@Composable
fun MyApp(animeViewModel: AnimeViewModel, movieViewModel: MovieViewModel) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "movies") {
        // Movie routes
        composable("movies") {
            MovieMainScreen(navController = navController, viewModel = movieViewModel)
        }
        composable("movie_detail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            val uiState = movieViewModel.uiState.collectAsState()
            val movies = (uiState.value as? MovieUiState.Success)?.movies
            val movie = movies?.find { it.id == movieId }
            movie?.let {
                MovieDetailScreen(movie = it, navController = navController)
            }
        }

        // Anime routes
        composable("main") { MainScreen(navController, animeViewModel) }
        composable("details/{title}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            DetailsScreen(title = title, navController)
        }
        composable("setting") { SettingScreen(navController, movieViewModel) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieMainScreen(navController: NavHostController, viewModel: MovieViewModel) {
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
        MovieScreen(
            navController = navController,
            viewModel = viewModel,
            paddingValues = innerPadding
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, viewModel: AnimeViewModel) {
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
        Main(paddingValues = innerPadding, navController = navController, viewModel = viewModel)
    }
}

@Composable
fun Main(paddingValues: PaddingValues, navController: NavHostController, viewModel: AnimeViewModel) {
    LazyColumn(contentPadding = paddingValues) {
        item {
            AnimeCarousel(animeList = animeList)
        }
        items(animeList) { anime ->
            AnimeList(anime = anime, navController = navController, viewModel = viewModel)
        }
    }
}
package com.example.composeModul5.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composeModul5.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeModul5.ui.components.MovieCarousel
import com.example.composeModul5.ui.components.MovieList
import com.example.composeModul5.viewmodel.MovieUiState
import com.example.composeModul5.viewmodel.MovieViewModel

@Composable
fun MovieScreen(
    navController: NavHostController,
    viewModel: MovieViewModel,
    paddingValues: PaddingValues = PaddingValues()
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is MovieUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is MovieUiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = (uiState as MovieUiState.Error).message)
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(onClick = { viewModel.loadMovies() }) {
                        Text(stringResource(R.string.retry))
                    }
                }
            }
        }

        is MovieUiState.Success -> {
            val movies = (uiState as MovieUiState.Success).movies
            LazyColumn(contentPadding = paddingValues) {
                item {
                    MovieCarousel(movies = movies.take(5))
                }
                items(movies) { movie ->
                    MovieList(movie = movie, navController = navController)
                }
            }
        }
    }
}
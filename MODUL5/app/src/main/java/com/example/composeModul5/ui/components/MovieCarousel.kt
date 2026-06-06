package com.example.composeModul5.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.example.composeModul5.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composeModul5.model.Movie

@Composable
fun MovieCarousel(movies: List<Movie>) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { Int.MAX_VALUE }
    )

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = stringResource(R.string.featured),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 12.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) { page ->
            val movie = movies[page % movies.size]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w780${movie.backdropPath}",
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 2
                            )
                            Text(
                                text = movie.releaseDate.take(4),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(movies.size.coerceAtMost(10)) { index ->
                val isSelected = pagerState.currentPage % movies.size == index
                Surface(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 8.dp else 6.dp),
                    shape = RoundedCornerShape(50),
                    color = if (isSelected)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                ) {}
            }
        }
    }
}
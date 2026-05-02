package com.example.composeModul3.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.composeModul3.model.Anime
import androidx.compose.ui.res.stringResource
import com.example.composeModul3.R

@Composable
fun AnimeCarousel(animeList: List<Anime>) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { Int.MAX_VALUE })

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = (stringResource(R.string.featured)),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 12.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)) {
            page ->
            val anime = animeList[page % animeList.size]
            CarouselItem(anime = anime)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center) {
            repeat(animeList.size) { index ->
                val isSelected = pagerState.currentPage % animeList.size == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 8.dp else 6.dp)
                        .then(
                            Modifier.padding(0.dp)),
                    contentAlignment = Alignment.Center) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(50),
                        color = if (isSelected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)) {}
                }
            }
        }
    }
}

@Composable
fun CarouselItem(anime: Anime) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(12.dp)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = anime.imageBg),
                contentDescription = anime.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize())
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(12.dp)) {
                Column {
                    Text(
                        text = anime.title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2)
                    Text(
                        text = anime.premiered,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White)
                }
            }
        }
    }
}
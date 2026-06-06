package com.example.composeModul3.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.res.stringResource
import com.example.composeModul3.R
import com.example.composeModul3.model.Anime

@Composable
fun AnimeList(anime: Anime, navController: NavHostController) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = anime.image),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp)))

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween)  {
                    Text(
                        text = anime.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f))
                    Text(
                        text = anime.premiered,
                        style = MaterialTheme.typography.bodySmall)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = (stringResource(anime.description)),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 4)

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                    OutlinedButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW) .apply {
                                data = Uri.parse(anime.webUrl)
                                setPackage("com.crunchyroll.crunchyroid")
                            }
                            if(intent.resolveActivity(context.packageManager) != null){
                                context.startActivity(intent)
                            } else {
                                val fallback = Intent(Intent.ACTION_VIEW, Uri.parse(anime.webUrl))
                                context.startActivity(fallback)
                            }
                        },
                        shape = RoundedCornerShape(12.dp)) {
                        Text(stringResource(R.string.watch))
                    }

                    OutlinedButton(
                        onClick = {
                            navController.navigate("details/${anime.title}") },
                        shape = RoundedCornerShape(12.dp)) {
                        Text(stringResource(R.string.detail))
                    }
                }
            }
        }
    }
}
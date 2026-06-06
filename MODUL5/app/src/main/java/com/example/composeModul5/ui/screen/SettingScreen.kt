package com.example.composeModul5.ui.screen

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavHostController
import com.example.composeModul5.R
import com.example.composeModul5.data.LanguagePreferences
import com.example.composeModul5.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavHostController, movieViewModel: MovieViewModel) {
    val context = LocalContext.current
    val languagePrefs = LanguagePreferences(context)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_title),
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.select_language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = {
                    languagePrefs.setLanguage(LanguagePreferences.LANG_ENGLISH)
                    setLocale(context, "en")
                    movieViewModel.reloadMovies()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.english))
            }

            Button(
                onClick = {
                    languagePrefs.setLanguage(LanguagePreferences.LANG_INDONESIAN)
                    setLocale(context, "in")
                    movieViewModel.reloadMovies()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.indonesian))
            }

            Button(
                onClick = { navController.navigateUp() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF2D1B45),
                    containerColor = Color(0xFFB39DDB)
                )
            ) {
                Text(text = stringResource(R.string.back))
            }
        }
    }
}

fun setLocale(context: Context, language: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java)
            .applicationLocales = LocaleList.forLanguageTags(language)
    } else {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(language)
        )
    }
}